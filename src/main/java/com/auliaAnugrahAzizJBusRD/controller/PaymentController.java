package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.*;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController implements BasicGetController<Payment> {
    public static @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\payment.json") JsonTable<Payment> paymentTable;

    public PaymentController() {}

    public JsonTable<Payment> getJsonTable() {
        return this.paymentTable;
    }

    @RequestMapping(value="/makeBooking", method= RequestMethod.POST)
    public BaseResponse<Payment> makeBooking(
            @RequestParam int buyerId,
            @RequestParam int renterid,
            @RequestParam int busId,
            @RequestParam List<String> busSeats,
            @RequestParam String departureDate
    ) {
        Predicate<Account> predAccount = a -> a.id == buyerId && a.company.id == renterid;
        Predicate<Bus> predBus = b -> b.id == busId;
        boolean exist =
        Algorithm.exists(AccountController.accountTable, predAccount) &&
        Algorithm.exists(BusController.busTable, predBus);
        Payment payment = null;
        if(exist) {
            Account acc = Algorithm.find(AccountController.accountTable, predAccount);
            Bus bus = Algorithm.find(BusController.busTable, predBus);

            if(acc.balance > bus.price.price) {
                Predicate<Payment> predPayment = p -> p.departureDate.toString() == departureDate;
                payment = Algorithm.find(getJsonTable(), predPayment);
            }
        }

        if(payment == null) {
            return new BaseResponse<>(false, "Gagal membuat booking", payment);
        } else {
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
             payment = Algorithm.find(getJsonTable(), pred);
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
            payment = Algorithm.find(getJsonTable(), pred);
            payment.status = Invoice.PaymentStatus.FAILED;
            return new BaseResponse<>(true, "Sukses meng-cancel booking", payment);
        }
        return new BaseResponse<>(false, "Gagal meng-cancel booking", payment);
    }

}
