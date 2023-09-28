package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Invoice here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */

import java.util.Calendar;
import java.util.Date;

public class Invoice extends Serializable
{
    public Calendar time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;
    
    protected Invoice(int id, int buyerId, int renterId) {
        super(id);
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = Calendar.getInstance();
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    
    public Invoice(int id, Account buyer, Renter renter) {
        super(id);
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.time = Calendar.getInstance();
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    
    public String toString() {
        return "Id: " + Integer.toString(this.id) + "\nBuyerId: " + Integer.toString(this.buyerId) + "\nRenterId: " + Integer.toString(this.renterId) + "\nTime: " + this.time;
    }
    
    public enum BusRating {
        NONE,
        NEUTRAL,
        GOOD,
        BAD;
    }
    
    public enum PaymentStatus {
        FAILED,
        WAITING,
        SUCCESS;
    }
}
