package auliaAnugrahAzizJBusRD;

import java.util.Calendar;
import java.text.*;

/**
 * Write a description of class Payment here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Payment extends Invoice
{
    private int busId;
    public Calendar departureDate;
    public String busSeat;
    
    public Payment(int id, int buyerId, int renterId, int busId, String busSeat) {
        super(id, buyerId, renterId);
        
        this.busId = busId;
        this.departureDate = Calendar.getInstance();
        this.departureDate.add(Calendar.DATE, 2);
        this.busSeat = busSeat;
    }
    
    public Payment(int id, Account buyer, Renter renter, String time, int busId, String busSeat) {
        super(id, buyer.id, renter.id);
        departureDate.add(Calendar.DATE, 2);
        this.busId = busId;
        this.departureDate = Calendar.getInstance();
        this.departureDate.add(Calendar.DATE, 2);
        this.busSeat = busSeat;
    }
    
    //public String toString() {
    //    return "Id: " + Integer.toString(this.id) + "\nBuyerId: " + Integer.toString(this.buyerId) + "\nRenterId: " + Integer.toString(this.renterId) + "\nTime: " + this.time + "\nBusId: " + Integer.toString(this.busId) + "\nDepartureDate: " + this.departureDate + "\nBusSeat: " + this.busSeat;
    //}
    
    public int getBusId() {
        return this.busId;
    }
    
    public String getDepartureInfo() {
        SimpleDateFormat SDFormat = new SimpleDateFormat("'Formatted Date: ' MMMM dd, yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String curr_date = SDFormat.format(cal.getTime());
        return "Current Date: " + curr_date + " Id: " + Integer.toString(this.id) + "\nBuyerId: " + Integer.toString(this.buyerId) + "\nRenterId: " + Integer.toString(this.renterId) + "\nTime: " + this.time + "\nBusId: " + Integer.toString(this.busId) + "\nDepartureDate: " + this.departureDate + "\nBusSeat: " + this.busSeat;
    }
    
    public String getTime() {
        SimpleDateFormat SDFormat = new SimpleDateFormat("'Formatted Date: ' MMMM dd, yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String curr_date = SDFormat.format(cal.getTime());
        return curr_date;
    }
}
