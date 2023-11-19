package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.Account;
import com.auliaAnugrahAzizJBusRD.Algorithm;
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
        Account account = new Account(name, email, password);
        String generatedPassword = null;
        boolean notValid = name.isBlank() || email.isBlank() || password.isBlank() || !account.validate() || Algorithm.exists(getJsonTable(), account);
        if(notValid) {
            return new BaseResponse<>(false, "Gagal register", null);
        }

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

        Account generatedAccount= new Account(name, email, generatedPassword);
        accountTable.add(generatedAccount);
        return new BaseResponse<>(true, "Berhasil register", generatedAccount);
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

        for(Account a : getJsonTable()) {
            if(a.email.equals(email) && a.password.equals(generatedPassword)) {
                foundedName = a.name;
                foundedEmail = a.email;
                foundedPassword = a.password;
            }
        }

        Account account = new Account(foundedName, foundedEmail, foundedPassword);
        if(foundedName.equals(null) && foundedEmail.equals(null) && foundedPassword.equals(null)) {
            return new BaseResponse<>(false, "Gagal login", account);
        } else {
            return new BaseResponse<>(true, "Berhasil login", account);
        }
    }

    @PostMapping("/{id}/registerRenter")
    protected BaseResponse<Renter> registerRenter(
            @PathVariable int id,
            @RequestParam String companyName,
            @RequestParam String address,
            @RequestParam String phoneNumber
    ) {
        boolean status = false;
        Renter renter = new Renter(companyName, phoneNumber, address);
        for(Account a : getJsonTable()) {
            if(a.id == id && a.company == null) {
                a.company = renter;
                status = true;
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
        for(Account a : this.getJsonTable()) {
            if(a.id == id) {
                boolean status = a.topUp(amount);
                if(status) {
                    return new BaseResponse<>(status, "Berhasil Top Up", amount);
                } else {
                    return new BaseResponse<>(status, "Gagal Top Up", amount);
                }
            }
        }
        return new BaseResponse<>(false, "Account not found", amount);
    }
}
