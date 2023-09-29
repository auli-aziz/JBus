package auliaAnugrahAzizJBusRD;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;


/**
 * Write a description of class Bus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bus extends Serializable implements FileParser
{
    public int capacity;
    public Facility facility;
    public String name;
    public Price price;
    public Station departure;
    public Station arrival;
    public BusType busType;
    public City city;
    public List<Schedule> schedules;
    
    public Bus(int id, String name, Facility facility, Price price, int capacity, BusType busType, City city, Station departure, Station arrival, List<Schedule> schedules) {
        super(id);
        this.capacity = capacity;
        this.facility = facility;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.busType = busType;
        this.departure = departure;
        this.arrival = arrival;
        this.city = city;
        this.schedules = new ArrayList<>();
    }
    
    public String toString() {
        return "Id: " + this.id + "\nName: " + this.name + "\nFacility: " + this.facility + this.price + "\nCapacity: " + this.capacity + "\nBusType: " + this.busType + "\nCity: " + this.city + this.departure + this.arrival;
    }
    
    public void addSchedule(Calendar calendar) {
        this.schedules.add(new Schedule(calendar, capacity));
    }
    
    public void printSchedule(Schedule schedule) {
        SimpleDateFormat SDFormat = new SimpleDateFormat("MMMM, yyyy HH:mm:ss");
        String curr_date = SDFormat.format(schedule.departureSchedule);
        System.out.println("Tanggal keberangkatan: " + curr_date);
        for(int i = 1; i <= schedule.seatAvailability.size(); i++) {
            System.out.print("RD" + i + ": ");
            System.out.println(schedule.seatAvailability.get("RD" + (i - 1)));
        }
    }
    
    public boolean read() {
        return false;
    }
    
    public Object write(){
        return null;
    }
}