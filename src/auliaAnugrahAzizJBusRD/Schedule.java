package auliaAnugrahAzizJBusRD;

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
 * @version (a version number or a date)
 */
public class Schedule
{
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;
    
    public Schedule(Timestamp departureSchedule, int numberOfSeats) {
        this.departureSchedule = departureSchedule;
        initializeSeatAvailability(numberOfSeats);
    }
    
    private void initializeSeatAvailability(int numberOfSeats) {
        this.seatAvailability  = new LinkedHashMap<String, Boolean>();
        for(int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++) {
            String sn = seatNumber < 10 ? "0" + seatNumber : "" + seatNumber;
            this.seatAvailability.put("RD" + sn, true);    
        }
        
        
    }

    // TODO: printSchedule tidak mempunyai parameter
    public void printSchedule(Schedule schedule) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
        String formattedDepartureSchedule = dateFormat.format(this.departureSchedule.getTime());
        
        // Print tanggal keberangkatan
        System.out.println("Tanggal keberangkatan: " + formattedDepartureSchedule);
        // Print daftar kursi dan ketersediaan kursinya
        System.out.println("Daftar kursi dan ketersediaan kursinya: ");
        // Create a list of seats and sort them numerically
        int maxSeatsPerRow = 4;
        int currentSeat = 1;
        
        for(String seat : this.seatAvailability.keySet()) {
            String symbol = this.seatAvailability.get(seat) ? "O" : "X";
            System.out.print(seat + " : " + symbol + "\t");
            
            if(currentSeat % maxSeatsPerRow == 0) {
                System.out.println();
            }
            currentSeat++;
        }
        System.out.println("\n");
    }
    
    public boolean isSeatAvailable(String seat) {
        return seatAvailability.getOrDefault(seat, false);
    }
    public boolean isSeatAvailable(List<String> seatList) {
        boolean status = false;
        for(String seat : seatList) {
            if(seatAvailability.getOrDefault(seat, false)) {
                status = true;
            }
        }
        return status; // ini menandakan bus sudah penuh (?)
    }
    
    public void bookSeat(String seat) {
        this.seatAvailability.put(seat, false);
    }

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
