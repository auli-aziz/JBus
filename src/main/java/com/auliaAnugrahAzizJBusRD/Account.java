package com.auliaAnugrahAzizJBusRD;


import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write a description of class Account here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Account extends Serializable
{
    public String email;
    public String name;
    public String password;
    public Renter company;
    public double balance;
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9]+@[a-zA-Z_]+?\\.[a-zA-Z.]+[a-zA-Z]+$";
    public static final String REGEX_PASSWORD = "^^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    public Account(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = 0;
        this.company = null;
    }
    
    public String toString() {
        return "Id: " + this.id + "\nName: " + this.name + "\nEmail: " + this.email + "\nPassword: " + this.password;
    }
    
    public Object write() {
        return null;
    }
    
    public boolean read(String file) {
        return false;
    }

    public boolean validate() {
        Pattern emailPattern = Pattern.compile(this.REGEX_EMAIL);
        Pattern passwordPattern = Pattern.compile(this.REGEX_PASSWORD);
        Matcher emailMatcher = emailPattern.matcher(this.email);
        Matcher passwordMatcher = passwordPattern.matcher(this.password);

        return emailMatcher.matches() && passwordMatcher.matches();
    }

    public boolean topUp(double amount) {
        if(amount > 0) {
            this.balance = this.balance + amount;
        }
        return (amount <= 0) ? false : true;
    }
}
