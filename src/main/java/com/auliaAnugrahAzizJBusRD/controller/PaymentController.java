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
                Payment payment = new Payment(buyerId, renterId, busId, busSeats, departureTimestamp);
                // TODO: Check if the seat is still available
                boolean isBooked = payment.makeBooking(departureTimestamp, busSeats, bus);
                if(!isBooked) {
                    payment.status = Invoice.PaymentStatus.WAITING;
                    paymentTable.add(payment);
                }
//                else {
//                    return new BaseResponse<>(false, "Kursi sudah di-book", null);
//                }
                return new BaseResponse<>(true, "Berhasil membuat booking", payment);
            } else {
                return new BaseResponse<>(false, "Kekurangan saldo", null);
            }

        } else {
            return new BaseResponse<>(false, "Gagal membuat booking", null);
        }
    }

    @RequestMapping(value="/{id}/accept", method= RequestMethod.POST)
    public BaseResponse<Payment> accept(@PathVariable int id) {
        Predicate<Payment> pred = p -> p.id == id;
        if(Algorithm.exists(getJsonTable(), pred)) {
            Payment payment = Algorithm.find(this.paymentTable, pred);
            if(payment.status == Invoice.PaymentStatus.FAILED) {
                return new BaseResponse<>(false, "Booking telah dicanc", null);
            }
            payment.status = Invoice.PaymentStatus.SUCCESS;
            return new BaseResponse<>(true, "Sukses menerima booking", payment);
        }
        return new BaseResponse<>(false, "Gagal menerima booking", null);
    }

    @RequestMapping(value="/{id}/cancel", method=RequestMethod.POST)
    public BaseResponse<Payment> cancel(@PathVariable int id) {
        Predicate<Payment> pred = p -> p.id == id;
        Payment payment = null;
        if(Algorithm.exists(getJsonTable(), pred)) {
            payment = Algorithm.find(this.paymentTable, pred);
            payment.status = Invoice.PaymentStatus.FAILED;
            return new BaseResponse<>(true, "Sukses meng-cancel booking", payment);
        }
        return new BaseResponse<>(false, "Gagal meng-cancel booking", payment);
    }

    @GetMapping("/getPaymentRequests")
    public List<Payment> getPaymentRequests(@RequestParam int busId) {
        return Algorithm.<Payment>collect(getJsonTable(), p -> p.getBusId() == busId);
    }
}
