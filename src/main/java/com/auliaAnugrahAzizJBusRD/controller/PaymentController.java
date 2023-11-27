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
        Timestamp timestamp = Timestamp.valueOf(departureDate);
        Predicate<Account> predAccount = a -> a.id == buyerId;
        Predicate<Bus> predBus = b -> b.id == busId;

        Account acc = Algorithm.find(AccountController.accountTable, predAccount);
        Bus bus = Algorithm.find(BusController.busTable, predBus);

        boolean statusSched = false;

        for(int i = 0; i < bus.schedules.size(); i++) {
            if(bus.schedules.get(i).departureSchedule.equals(timestamp)) {
                System.out.println(bus.schedules.get(i).departureSchedule);
                statusSched = true;
            }
        }

        Payment payment = null;
        if(acc != null && bus != null && statusSched) {
            if(acc.balance >= bus.price.price * busSeats.size()) {
                payment = new Payment(buyerId, renterId, busId, busSeats, timestamp);
            } else {
                return new BaseResponse<>(false, "Kekurangan saldo", payment);
            }
        }

        if(payment == null) {
            return new BaseResponse<>(false, "Gagal membuat booking", payment);
        } else {
            payment.makeBooking(timestamp, busSeats, bus);
            payment.status = Invoice.PaymentStatus.WAITING;
            paymentTable.add(payment);
            return new BaseResponse<>(true, "Berhasil membuat booking", payment);
        }
    }

    @RequestMapping(value="/{id}/accept", method= RequestMethod.POST)
    public BaseResponse<Payment> accept(@PathVariable int id) {
        Predicate<Payment> pred = p -> p.id == id;
        Payment payment = null;
        if(Algorithm.exists(getJsonTable(), pred)) {
             payment = Algorithm.find(this.paymentTable, pred);
             payment.status = Invoice.PaymentStatus.SUCCESS;
            return new BaseResponse<>(true, "Sukses menerima booking", payment);
        }
        return new BaseResponse<>(false, "Gagal menerima booking", payment);
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

}
