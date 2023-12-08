package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.*;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController implements BasicGetController<Payment> {
    public static @JsonAutowired(value = Payment.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\payment.json") JsonTable<Payment> paymentTable;

    public PaymentController() {}

    public JsonTable<Payment> getJsonTable() {
        return this.paymentTable;
    }

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
        Predicate<Bus> predBus = b -> b.id == busId;

        // Check the existence of buyer account and bus
        Account buyerAcc = Algorithm.find(AccountController.accountTable, predBuyer);
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
            double totalPrice = bus.price.price * busSeats.size();
            if(buyerAcc.balance >= totalPrice) {
                buyerAcc.balance -= totalPrice;
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

    @RequestMapping(value="/{id}/cancel", method=RequestMethod.POST)
    public BaseResponse<Payment> cancel(
            @PathVariable int id,
            @RequestParam int buyerId,
            @RequestParam int busId
    ) {
        Predicate<Payment> predPay = p -> p.id == id;
        Predicate<Account> predAcc = a -> a.id == buyerId;
        Predicate<Bus> predBus = b -> b.id == busId;
        Payment payment = Algorithm.find(this.paymentTable, predPay);
        Account account = Algorithm.find(AccountController.accountTable, predAcc);
        Bus bus = Algorithm.find(BusController.busTable, predBus);

        if(payment != null && account != null && bus != null) {
            account.balance += bus.price.getRebatedPrice();
            payment.status = Invoice.PaymentStatus.FAILED;
            return new BaseResponse<>(true, "Booking Canceled", payment);
        }
        return new BaseResponse<>(false, "Booking Cancel Failed", null);
    }

    @GetMapping("/getPayments")
    public List<Payment> getPayments(@RequestParam int buyerId) {
        return Algorithm.<Payment>collect(getJsonTable(), p -> p.buyerId == buyerId);
    }

    @GetMapping("/getPaymentRequests")
    public List<Payment> getPaymentRequests(@RequestParam int busId) {
        return Algorithm.<Payment>collect(getJsonTable(), p -> p.getBusId() == busId);
    }
}
