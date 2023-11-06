package auliaAnugrahAzizJBusRD;


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
    public static final String REGEX_EMAIL = "^[A-Za-z0-9]+s*@[A-Za-z]+(.[A-Za-z]+)+$";
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$";

    public Account(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
}
