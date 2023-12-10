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

/**
 * Handles all the API requests that is related to manipulating data on account.json
 *
 * @author Aulia Anugrah Aziz
 * @version 10 December 2023
 */
@RestController
@RequestMapping("/account")
public class AccountController implements BasicGetController<Account>
{
    public static @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\account.json") JsonTable<Account> accountTable;

    public AccountController() {}

    public JsonTable<Account> getJsonTable() {
        return this.accountTable;
    }

    /**
     * Used to register an account in JBus and generate hashed password using MD5
     *
     * @param name      name of the account that will be inputted into the database
     * @param email     email of the account that will be validated in Account
     * @param password  password will be validated in Account
     * @return <code>new BaseResponse<>(false, "Register Failed, enter all required fields", null)</code>
     *          if all fields has not been filled
     *          <code>new BaseResponse<>(false, "Register Failed, email already registered", null)</code>
     *          if there is an email with the same name in the database
     *          <code>new BaseResponse<>(false, "Register Failed, enter valid password and email", null)</code>
     *          if there email and password fails validate
     *          <code>new BaseResponse<>(true, "Register Success", account)</code>
     *          if the account has been instantiated and added to the json database
     * @see Account#validate()
     */
    @PostMapping("/register")
    protected BaseResponse<Account> register
            (
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password
            )
    {
        if(name.isBlank() || email.isBlank() || password.isBlank()) {
            return new BaseResponse<>(false, "Register Failed, enter all required fields", null);
        }

        for(int i = 0; i < this.accountTable.size(); i++) {
            if(this.accountTable.get(i).email.equals(email)) {
                return new BaseResponse<>(false, "Register Failed, email already registered", null);
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
            return new BaseResponse<>(false, "Register Failed, enter valid password and email", null);
        }

        account.password = generatedPassword;
        accountTable.add(account);
        return new BaseResponse<>(true, "Register Success", account);
    }

    /**
     * Used to log in to JBus
     *
     * @param email     registered email
     * @param password  plaintext password with the corresponding email (will be hashed in the code)
     * @return          <code>new BaseResponse<>(true, "Welcome to JBus!", a)</code> if there is
     *                  the email that matches the same hashed password in the json database
     *                  <code>new BaseResponse<>(false, "Login Failed", null)</code> if the email
     *                  and password does not match any of the entries the json database
     */
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
                return new BaseResponse<>(true, "Welcome to JBus!", a);
            }
        }

        return new BaseResponse<>(false, "Login Failed", null);
    }

    /**
     * regsterRenter adds a renter field to the account to create new bus
     *
     * @param id            ID of the account
     * @param companyName   company name entered by the user
     * @param address       address of the company
     * @param phoneNumber   company phone number
     * @return              <code>new BaseResponse<>(true, "Renter Register Success", renter)</code>
     *                      if account with the given id is not null
     *                      <code>new BaseResponse<>(false, "Renter Register Failed", null)</code>
     *                      if account already has renter field or the account is not found
     */
    @PostMapping("/{id}/registerRenter")
    protected BaseResponse<Renter> registerRenter(
            @PathVariable int id,
            @RequestParam String companyName,
            @RequestParam String address,
            @RequestParam String phoneNumber
    ) {
        Predicate<Account> pred = a -> a.id == id && a.company == null;
        Account acc = Algorithm.find(getJsonTable(), pred);
        if(acc != null) {
            Renter renter = new Renter(companyName, phoneNumber, address);
            acc.company = renter;
            return new BaseResponse<>(true, "Renter Register Success", renter);
        }
        return new BaseResponse<>(false, "Renter Register Failed", null);
    }

    /**
     *
     * @param id        ID of the account
     * @param amount    amount that will be topped up to account balance
     * @return          <code>new BaseResponse<>(status, "Top Up Success", amount)</code>
     *                  if account matches the ID
     *                  <code>new BaseResponse<>(false, "Top Up Failed", amount)</code>
     *                  if the amount topped up is a negative number
     *                  <code>new BaseResponse<>(false, "Account not found", null)</code>
     *                  if account is not found
     * @see Account#topUp(double)
     */
    @PostMapping("/{id}/topUp")
    protected BaseResponse<Double> topUp(
            @PathVariable int id,
            @RequestParam double amount
    ) {
        Predicate<Account> pred = a -> a.id == id;
        Account acc = Algorithm.find(getJsonTable(), pred);
        if(acc != null) {
            return (acc.topUp(amount))?
                new BaseResponse<>(true, "Top Up Success", amount):
                new BaseResponse<>(false, "Top Up Failed", amount);
        } else{
            return new BaseResponse<>(false, "Account not found", null);
        }
    }
}
