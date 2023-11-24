package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.Account;
import com.auliaAnugrahAzizJBusRD.Algorithm;
import com.auliaAnugrahAzizJBusRD.Predicate;
import com.auliaAnugrahAzizJBusRD.Renter;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("/account")
public class AccountController implements BasicGetController<Account>
{
    public static @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\account.json") JsonTable<Account> accountTable;
//    @GetMapping
//    String index() { return "account page"; }
//
//    @PostMapping("/register")
//    Account register
//            (
//                    @RequestParam String name,
//                    @RequestParam String email,
//                    @RequestParam String password
//            )
//    {
//        return new Account(name, email, password);
//    }
//
//    @GetMapping("/{id}")
//    String getById(@PathVariable int id) { return "account id " + id + " not found!"; }

    public AccountController() {}

    public JsonTable<Account> getJsonTable() {
        return this.accountTable;
    }

    @PostMapping("/register")
    protected BaseResponse<Account> register
            (
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password
            )
    {
        if(name.isBlank() || email.isBlank() || password.isBlank()) {
            return new BaseResponse<>(false, "Gagal register, masukkan semua data", null);
        }

        for(int i = 0; i < this.accountTable.size(); i++) {
            if(this.accountTable.get(i).email.equals(email)) {
                return new BaseResponse<>(false, "Gagal register, email sudah terdaftar", null);
            }
        }

        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Account account = new Account(name, email, password);
        if(!account.validate()) {
            account = null;
            return new BaseResponse<>(false, "Gagal register, masukkan password atau email sesuai syarat", null);
        }

        account.password = generatedPassword;
        accountTable.add(account);
        return new BaseResponse<>(true, "Berhasil register", account);
    }

    @PostMapping("/login")
    protected BaseResponse<Account> login
            (
                    @RequestParam String email,
                    @RequestParam String password
            ) {
        String generatedPassword = null;
        String foundedName = null;
        String foundedEmail = null;
        String foundedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for(Account a : this.accountTable) {
            if(a.email.equals(email) && a.password.equals(generatedPassword)) {
                return new BaseResponse<>(true, "Welcome to JBus!", a);
            }
        }

        return new BaseResponse<>(false, "Gagal login", null);
    }

    @PostMapping("/{id}/registerRenter")
    protected BaseResponse<Renter> registerRenter(
            @PathVariable int id,
            @RequestParam String companyName,
            @RequestParam String address,
            @RequestParam String phoneNumber
    ) {
        Renter renter = new Renter(companyName, phoneNumber, address);
        for(Account a : this.accountTable) {
            if(a.id == id && a.company == null) {
                a.company = renter;
                return new BaseResponse<>(true, "Berhasil membuat renter", renter);
            }
        }
        return new BaseResponse<>(false, "Gagal membuat renter tidak ditemukan id", renter);
    }

    @PostMapping("/{id}/topUp")
    protected BaseResponse<Double> topUp(
            @PathVariable int id,
            @RequestParam double amount
    ) {
        int counter = 0;
        for(Account a : this.accountTable) {
            if(a.id == id) {
                boolean status = a.topUp(amount);
                if(status) {
                    return new BaseResponse<>(status, "Berhasil Top Up IDR " + amount, amount);
                } else {
                    return new BaseResponse<>(status, "Gagal Top Up", amount);
                }
            }
            counter++;
        }
        return new BaseResponse<>(false, "Account not found", amount);
    }
}
