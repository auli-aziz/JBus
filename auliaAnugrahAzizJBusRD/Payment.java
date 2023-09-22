package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Payment here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Payment extends Invoice
{
    private int busId;
    public String departureDate;
    public String busSeat;
    
    public Payment(int id, int buyerId, int renterId, String time, int busId, String departureDate, String busSeat) {
        super(id, renterId, renterId, time);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public Payment(int id, Account buyer, Renter renter, String time, int busId, String departureDate, String busSeat) {
        super(id, buyer, renter, time);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public String print() {
        String returnVal = this.departureDate + " " + this.busSeat + " " + Integer.toString(busId);
        return returnVal;
    }
    
    public int getBusId() {
        return this.busId;
    }
}
