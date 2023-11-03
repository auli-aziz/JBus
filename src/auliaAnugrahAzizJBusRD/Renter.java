package auliaAnugrahAzizJBusRD;

import java.util.regex.*;

/**
 * Write a description of class Renter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Renter extends Serializable
{
    public String address;
    public String companyName;
    public int phoneNumber;
    private final String REGEX_NAME = "^^[A-Z][a-zA-Z0-9]{4,20}$";
    private final String REGEX_PHONE = "^[0-9]{9,12}$";
    
    public Renter(String companyName) {
        this.companyName = companyName;
        this.address = "";
        this.phoneNumber = 0;
    }
    
    public Renter(String companyName, String address) {
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = 0;
    }
    
    public Renter(String companyName, int phoneNumber) {
        this.companyName = companyName;
        this.address = "";
        this.phoneNumber = phoneNumber;
    }
    
    public Renter(String companyName, int phoneNumber, String address) {
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public boolean validate() {
        Pattern phonePattern = Pattern.compile(this.REGEX_PHONE);
        Pattern namePattern = Pattern.compile(this.REGEX_NAME);

        String tempPhone = Integer.toString(this.phoneNumber);
        Matcher phoneMatcher = phonePattern.matcher(tempPhone);
        Matcher nameMatcher = namePattern.matcher(this.companyName);

        return phoneMatcher.find() && nameMatcher.find();

    }
}
