package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.*;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Handles all the API requests that is related to manipulating data on payment.json
 *
 * @author Aulia Anugrah Aziz
 * @version 10 December 2023
 */
@RestController
@RequestMapping("/payment")
public class PaymentController implements BasicGetController<Payment> {
    public static @JsonAutowired(value = Payment.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\payment.json") JsonTable<Payment> paymentTable;

    public PaymentController() {}

    public JsonTable<Payment> getJsonTable() {
        return this.paymentTable;
    }

    /**
     * makes bus booking and sets the payment status to AWAITING
     *
     * @param buyerId           the ID of the buyer that will do make booking
     * @param renterId          the ID of the seller that own the bus
     * @param busId             bus ID
     * @param busSeats          the seats that will be booked
     * @param departureDate     available departure dates
     * @return                  <code>new BaseResponse<>(false, "Renter does not exist", null)</code>
     *                          if Algorithm returns null for renter
     *                          <code>new BaseResponse<>(false, "Schedule does not exist", null)</code>
     *                          if Algorithm does not find any schedule
     *                          <code>new BaseResponse<>(false, "Seat already Booked", null)</code>
     *                          if the seat is already booked
     *                          <code>new BaseResponse<>(true, "Booking Successful", payment)</code>
     *                          if seat is booked successfully
     *                          <code>new BaseResponse<>(false, "Not Enough Balance", null)</code>
     *                          if buyer does not have enough balance
     * @see Algorithm
     * @see Payment#makeBooking(Timestamp, String, Bus)
     * @see Payment#makeBooking(Timestamp, List, Bus)
     */
    @RequestMapping(value="/makeBooking", method=RequestMethod.POST)
    public BaseResponse<Payment> makeBooking(
            @RequestParam int buyerId,
            @RequestParam int renterId,
            @RequestParam int busId,
            @RequestParam List<String> busSeats,
            @RequestParam String departureDate
    ) {
        Timestamp departureTimestamp = Timestamp.valueOf(departureDate);

        Predicate<Account> predBuyer = a -> a.id == buyerId;
        Predicate<Account> predSeller = a -> a.company.id == renterId;
        Predicate<Bus> predBus = b -> b.id == busId;

        // Check the existence of buyer account and bus
        Account buyerAcc = Algorithm.find(AccountController.accountTable, predBuyer);
        Account sellerAcc = Algorithm.find(AccountController.accountTable, predSeller);
        Bus bus = Algorithm.find(BusController.busTable, predBus);

        boolean isSchedExist = false;
        boolean isRenterExist = false;

        // Check if there is a renter with the given id
        for (Account a : AccountController.accountTable) {
            if(a.company == null) {
                continue;
            } else {
                if(a.company.id == renterId) isRenterExist = true;
            }
        }
        if(!isRenterExist) {
            return new BaseResponse<>(false, "Renter does not exist", null);
        }

        if(buyerAcc != null && bus != null) {

            // The departure date needs to match one of the bus schedules
            for(int i = 0; i < bus.schedules.size(); i++) {
                if(bus.schedules.get(i).departureSchedule.equals(departureTimestamp)) {
                    isSchedExist = true;
                }
            }
            if(!isSchedExist) {
                return new BaseResponse<>(false, "Schedule does not exist", null);
            }

            // Check for the balance of the buyer
            double totalPrice = JBus.getTotalPrice(bus.price.price, busSeats.size());
            if(buyerAcc.balance >= totalPrice) {
                buyerAcc.balance -= totalPrice;
                sellerAcc.balance += totalPrice;

                Payment payment = new Payment(buyerId, renterId, busId, busSeats, departureTimestamp);
                boolean isSuccess = payment.makeBooking(departureTimestamp, busSeats, bus);
                if(!isSuccess) {
                    return new BaseResponse<>(false, "Seat already Booked", null);
                }
                payment.status = Invoice.PaymentStatus.WAITING;
                paymentTable.add(payment);
                return new BaseResponse<>(true, "Booking Successful", payment);
            } else {
                return new BaseResponse<>(false, "Not Enough Balance", null);
            }

        } else {
            return new BaseResponse<>(false, "Booking Failed", null);
        }
    }

    /**
     * Accepts the payment
     *
     * @param id    ID of the payment from payment.json
     * @return      <code>new BaseResponse<>(false, "Booking has already been canceled", null)</code>
     *              if the payment status has already FAILED
     *              <code>new BaseResponse<>(true, "Booking Accepted", payment)</code>
     *              if the payments states was previously AWAITING
     *              <code>new BaseResponse<>(false, "Booking Rejected", null)</code>
     *              if payment object with the given ID does is not found
     */
    @RequestMapping(value="/{id}/accept", method= RequestMethod.POST)
    public BaseResponse<Payment> accept(@PathVariable int id) {
        Predicate<Payment> pred = p -> p.id == id;
        if(Algorithm.exists(getJsonTable(), pred)) {
            Payment payment = Algorithm.find(getJsonTable(), pred);
            if(payment.status == Invoice.PaymentStatus.FAILED) {
                return new BaseResponse<>(false, "Booking has already been canceled", null);
            }
            payment.status = Invoice.PaymentStatus.SUCCESS;
            return new BaseResponse<>(true, "Booking Accepted", payment);
        }
        return new BaseResponse<>(false, "Booking Rejected", null);
    }

    /**
     * Cancels the payment
     *
     * @param id            ID of the payment
     * @param buyerId       ID of the buyer account
     * @param busId         bus ID with the correspoding payment
     * @param renterId      ID of the seller account
     * @return              <code>new BaseResponse<>(true, "Booking Canceled", payment)</code>
     *                      <code>new BaseResponse<>(false, "Booking Cancel Failed", null)</code>
     */
    @RequestMapping(value="/{id}/cancel", method=RequestMethod.POST)
    public BaseResponse<Payment> cancel(
            @PathVariable int id,
            @RequestParam int buyerId,
            @RequestParam int busId,
            @RequestParam int renterId
    ) {
        Predicate<Payment> predPay = p -> p.id == id;
        Predicate<Account> predBuyer = a -> a.id == buyerId;
        Predicate<Account> predSeller = a -> a.company.id == renterId;
        Predicate<Bus> predBus = b -> b.id == busId;
        Payment payment = Algorithm.find(getJsonTable(), predPay);
        Account buyerAcc = Algorithm.find(AccountController.accountTable, predBuyer);
        Account sellerAcc = Algorithm.find(AccountController.accountTable, predSeller);
        Bus bus = Algorithm.find(BusController.busTable, predBus);

        if(payment != null && buyerAcc != null && bus != null) {
            buyerAcc.balance += bus.price.getRebatedPrice();
            sellerAcc.balance -= bus.price.rebate;

            payment.status = Invoice.PaymentStatus.FAILED;
            return new BaseResponse<>(true, "Booking Canceled", payment);
        }
        return new BaseResponse<>(false, "Booking Cancel Failed", null);
    }

    /**
     * used to display payment history
     *
     * @param buyerId   buyer ID
     * @return          payments from payment.json that matches buyer ID
     */
    @GetMapping("/getPayments")
    public List<Payment> getPayments(@RequestParam int buyerId) {
        return Algorithm.<Payment>collect(getJsonTable(), p -> p.buyerId == buyerId);
    }

    /**
     * used to display payments from other accounts that is interested in booking a seat
     *
     * @param busId     bus ID from bus.json
     * @return          payments from payment.json that matches bus ID
     */
    @GetMapping("/getPaymentRequests")
    public List<Payment> getPaymentRequests(@RequestParam int busId) {
        return Algorithm.<Payment>collect(getJsonTable(), p -> p.getBusId() == busId);
    }
}
