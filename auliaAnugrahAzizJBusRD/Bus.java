package auliaAnugrahAzizJBusRD;

import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;


/**
 * Write a description of class Bus here.
 *
 * @author Aulia Anugrah Aziz
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
    
    public Bus(int id, String name, Facility facility, Price price, int capacity, BusType busType, City city, Station departure, Station arrival) {
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
    
    public void addSchedule(Timestamp calendar) {
        this.schedules.add(new Schedule(calendar, capacity));
    }
    
    public boolean read(String file) {
        return false;
    }
    
    public Object write(){
        return null;
    }
}