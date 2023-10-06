package AuliaAnugrahAzizJBusRD;

import java.sql.Timestamp;
import java.text.*;

/**
 * Write a description of class Payment here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Payment extends Invoice
{
    private int busId;
    public Timestamp departureDate;
    public String busSeat;
    
    public Payment(int buyerId, int renterId, int busId, String busSeat, Timestamp departureDate) {
        super(buyerId, renterId);
        
        this.busId = busId;
        this.busSeat = busSeat;
        this.departureDate = departureDate;
    }
    
    public Payment(Account buyer, Renter renter, String time, int busId, String busSeat, Timestamp departureDate) {
        super(buyer.id, renter.id);
        
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    //public String toString() {
    //    return "Id: " + Integer.toString(this.id) + "\nBuyerId: " + Integer.toString(this.buyerId) + "\nRenterId: " + Integer.toString(this.renterId) + "\nTime: " + this.time + "\nBusId: " + Integer.toString(this.busId) + "\nDepartureDate: " + this.departureDate + "\nBusSeat: " + this.busSeat;
    //}
    
    public int getBusId() {
        return this.busId;
    }
    
    public String getDepartureInfo() {
        SimpleDateFormat SDFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        String curr_date = SDFormat.format(departureDate.getTime());
        return "Current Date: " + curr_date + " Id: " + Integer.toString(this.id) + "\nBuyerId: " + Integer.toString(this.buyerId) + "\nRenterId: " + Integer.toString(this.renterId) + "\nTime: " + this.time.getTime() + "\nBusId: " + Integer.toString(this.busId) + "\nDepartureDate: " + this.departureDate.getTime() + "\nBusSeat: " + this.busSeat;
    }
    
    public String getTime() {
        SimpleDateFormat SDFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        String curr_date = SDFormat.format(departureDate.getTime());
        return curr_date;
    }

    public static boolean isAvailable(Timestamp departureSchedule, String seat, Bus bus) {
        for(Schedule s : bus.schedules) {
            if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable(seat)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean makeBooking(Timestamp departureSchedule, String seat, Bus bus) {
        for(Schedule s : bus.schedules) {
            if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable(seat)) {
                s.bookSeat(seat);
                return true;
            }
        }
        return false;
    }
}

