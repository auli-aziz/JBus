package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Account here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Account extends Serializable implements FileParser
{
    public String email;
    public String name;
    public String password;
    
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
}
