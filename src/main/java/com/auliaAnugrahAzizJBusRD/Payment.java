package com.auliaAnugrahAzizJBusRD;

import java.sql.Timestamp;
import java.text.*;
import java.util.List;

/**
 * Makes payment object for JBus
 *
 * @author Aulia Anugrah Aziz
 * @version 10 December 2022
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

    /**
     *
     * @param departureSchedule
     * @param seat
     * @param bus
     * @return                  available schedule for a seat
     */
    public static Schedule availableSchedule(Timestamp departureSchedule, String seat, Bus bus) {
        for(Schedule s : bus.schedules) {
            if(s.departureSchedule.equals(departureSchedule) && s.isSeatAvailable(seat)) {
                return s;
            }
        }
        return null;
    }

    /**
     *
     * @param departureSchedule
     * @param seatList
     * @param bus
     * @return                      available schedule for a seat list
     */
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

    /**
     * Makes booking for one seat
     *
     * @param departureSchedule
     * @param seat
     * @param bus
     * @return                  boolean value of makeBooking - true if successful, false if failed
     */
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


    /**
     * Makes booking for a list of seat
     *
     * @param departureSchedule
     * @param seatList
     * @param bus
     * @return                      boolean value of makeBooking - true if successful, false if failed
     */
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

