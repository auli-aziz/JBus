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
        super(id, buyerId, renterId);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public Payment(int id, Account buyer, Renter renter, String time, int busId, String departureDate, String busSeat) {
        super(id, buyer.id, renter.id);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public String toString() {
        return "Id: " + Integer.toString(this.id) + "\nBuyerId: " + Integer.toString(this.buyerId) + "\nRenterId: " + Integer.toString(this.renterId) + "\nTime: " + this.time + "\nBusId: " + Integer.toString(this.busId) + "\nDepartureDate: " + this.departureDate + "\nBusSeat: " + this.busSeat;
    }
    
    public int getBusId() {
        return this.busId;
    }
}
