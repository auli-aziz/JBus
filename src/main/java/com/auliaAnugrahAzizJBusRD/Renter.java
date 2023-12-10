package com.auliaAnugrahAzizJBusRD;

import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;

import java.util.regex.*;

/**
 * Renter is used by an account to manage bus. Not all accounts are renter. To become a renter
 * the account needs to register as renter
 *
 * @author Aulia Anugrah Aziz
 * @version 10 Desember 2023
 */
public class Renter extends Serializable
{
    public String address;
    public String companyName;
    public String phoneNumber;
    private static final String REGEX_NAME = "^^[A-Z][a-zA-Z0-9]{4,20}$";
    private static final String REGEX_PHONE = "^[0-9]{9,12}$";
    
    public Renter(String companyName) {
        super();
        this.companyName = companyName;
        this.address = "";
        this.phoneNumber = "";
    }

    public Renter(String companyName, String phoneNumber) {
        super();
        this.companyName = companyName;
        this.address = "";
        this.phoneNumber = phoneNumber;
    }
    
    public Renter(String companyName, String phoneNumber, String address) {
        super();
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public boolean validate() {
        Pattern phonePattern = Pattern.compile(this.REGEX_PHONE);
        Pattern namePattern = Pattern.compile(this.REGEX_NAME);
        Matcher phoneMatcher = phonePattern.matcher(this.phoneNumber);
        Matcher nameMatcher = namePattern.matcher(this.companyName);

        return phoneMatcher.find() && nameMatcher.find();

    }
}
