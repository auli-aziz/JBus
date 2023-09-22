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
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = time;
    }
    
    public Invoice(int id, Account buyer, Renter renter, String time) {
        super(id);
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.time = time;
    }
    
    public String print() {
        String returnVal = time + " " + Integer.toString(this.buyerId) + " " + Integer.toString(this.renterId) + " " + Integer.toString(super.id);
        return returnVal;
    }
}
