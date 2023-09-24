package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Payment here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Payment extends Invoice
{
    protected int busId;
    public String departureDate;
    public String busSeat;
    
    public Payment(int id, int buyerId, int renterId, String time, int busId, String departureDate, String busSeat) {
        super(id, buyerId, renterId, time);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public Payment(int id, Account buyer, Renter renter, String time, int busId, String departureDate, String busSeat) {
        super(id, buyer.id, renter.id, time);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public String print() {
        String returnVal = Integer.toString(id) + " " + Integer.toString(buyerId) + " " + Integer.toString(renterId) + " " + time + " " + Integer.toString(busId) + " " + this.departureDate + " " + this.busSeat;
        return returnVal;
    }
    
    public int getBusId() {
        return this.busId;
    }
}
