package auliaAnugrahAzizJBusRD;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Write a description of class Schedule here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Schedule
{
    public Calendar departureSchedule;
    public Map<String, Boolean> seatAvailability;
    
    public Schedule(Calendar departureSchedule, int numberOfSeats) {
        this.departureSchedule = departureSchedule;
        initializeSeatAvailability(numberOfSeats);
    }
    
    private void initializeSeatAvailability(int numberOfSeats) {
        this.seatAvailability  = new LinkedHashMap<String, Boolean>();
        for(int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++) {
            this.seatAvailability.put("RD" + seatNumber, true);    
        }
        
        
    }
}
