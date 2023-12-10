package com.auliaAnugrahAzizJBusRD;

import java.sql.Timestamp;
import java.text.*;
import java.util.List;

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
    public List<String> busSeat;
    
    public Payment(int buyerId, int renterId, int busId, List<String> busSeat, Timestamp departureDate) {
        super(buyerId, renterId);
        
        this.busId = busId;
        this.busSeat = busSeat;
        this.departureDate = departureDate;
    }
    
    public Payment(Account buyer, Renter renter, int busId, List<String> busSeat, Timestamp departureDate) {
        super(buyer.id, renter.id);
        
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeat = busSeat;
    }
    
    public int getBusId() {
        return this.busId;
    }

    public static Schedule availableSchedule(Timestamp departureSchedule, String seat, Bus bus) {
        for(Schedule s : bus.schedules) {
            if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable(seat)) {
                return s;
            }
        }
        return null;
    }

    public static Schedule availableSchedule(Timestamp departureSchedule, List<String> seatList, Bus bus) {
        for(Schedule s : bus.schedules) {
            for(String seat : seatList) {
                if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable((seat))) {
                    return s;
                }
            }
        }
        return null;
    }

    // Metode ini melakukan booking utk 1 seat
    public static boolean makeBooking(Timestamp departureSchedule, String seat, Bus bus) {
        if(availableSchedule(departureSchedule, seat, bus) != null) {
            for(Schedule s : bus.schedules) {
                if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable(seat)) {
                    s.bookSeat(seat);
                    return true;
                }
            }
        }
            return false;
    }

    // Metode ini melakukan booking utk sejumlah seat
    public static boolean makeBooking(Timestamp departureSchedule, List<String> seatList, Bus bus) {
        for(Schedule s : bus.schedules) {
            if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable(seatList)) {
                s.bookSeat(seatList);
                return true;
            }
        }
        return false;
    }
}

