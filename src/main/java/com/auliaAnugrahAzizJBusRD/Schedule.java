package com.auliaAnugrahAzizJBusRD;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.text.SimpleDateFormat;

/**
 * Write a description of class Schedule here.
 *
 * @author Aulia Anugrah Aziz
 * @version 10 Desember 2023
 */
public class Schedule
{
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;

    public Schedule(Timestamp departureSchedule, int numberOfSeats) {
        this.departureSchedule = departureSchedule;
        initializeSeatAvailability(numberOfSeats);
    }

    /**
     * Creates new Seat list for a given schedule and sets availability to true
     *
     * @param numberOfSeats
     */
    private void initializeSeatAvailability(int numberOfSeats) {
        this.seatAvailability  = new LinkedHashMap<String, Boolean>();
        for(int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++) {
            String sn = seatNumber < 10 ? "0" + seatNumber : "" + seatNumber;
            this.seatAvailability.put("RD" + sn, true);    
        }
        
        
    }

    /**
     * Checks for seat availability of a single seat
     *
     * @param seat
     * @return      true if the seat is available, otherwise false
     */
    public boolean isSeatAvailable(String seat) {
        return seatAvailability.getOrDefault(seat, false);
    }

    /**
     * Checks for seat availability of a list of seats
     *
     * @param seatList
     * @return          true if the seats are available, otherwise false
     */
    public boolean isSeatAvailable(List<String> seatList) {
        for (String seat : seatList) {
            if (!seatAvailability.getOrDefault(seat, false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Books seat
     * @param seat
     */
    public void bookSeat(String seat) {
        this.seatAvailability.put(seat, false);
    }

    /**
     * Books seat list
     * @param seatList
     */
    public void bookSeat(List<String> seatList) {
        for(String seat : seatList) {
            if(seatAvailability.getOrDefault(seat, false)) {
                this.seatAvailability.put(seat, false); // ini booking semua seat (?)
            }
        }
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<Boolean> temp = new ArrayList<>();
        int totalCapacity = 0;
        for(String seat : this.seatAvailability.keySet()) {
            temp.add(seatAvailability.get(seat));
            totalCapacity++;
        }

        int result = Algorithm.count(temp, false);

        return "Schedule: " + sdf.format(this.departureSchedule) + "\tOccupied: " + Integer.toString(result) + "/" + Integer.toString(totalCapacity);
    }
}
