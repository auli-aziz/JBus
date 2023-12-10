package com.auliaAnugrahAzizJBusRD;


/**
 * Invoice is inherited by payment to store the timestamp of the payment,
 * buyer ID, renter ID, payment status and bus rating.
 *
 * @author Aulia Anugrah Aziz
 * @version 10 Desember 2023
 */

import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;

import java.sql.Timestamp;

public class Invoice extends Serializable
{
    public Timestamp time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;
    
    protected Invoice(int buyerId, int renterId) {
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    
    public Invoice( Account buyer, Renter renter) {
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.time = new Timestamp(System.currentTimeMillis());
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
