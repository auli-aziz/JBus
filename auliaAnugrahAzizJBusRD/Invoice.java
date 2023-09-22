package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Invoice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Invoice extends Serializable
{
    public String time;
    public int buyerId;
    public int renterId;
    
    protected Invoice(int id, int buyerId, int renterId, String time) {
        super(id);
    }
    
    public Invoice(int id, Account buyer, Renter renter, String time) {
        super(id);
    }
    
    public String print() {
        return time;
    }
}
