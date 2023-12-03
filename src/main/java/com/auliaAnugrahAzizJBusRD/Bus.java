package com.auliaAnugrahAzizJBusRD;

import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;

import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;


/**
 * Write a description of class Bus here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Bus extends Serializable
{
    public int capacity;
    public List<Facility> facility;
    public String name;
    public Price price;
    public Station departure;
    public Station arrival;
    public BusType busType;
    public List<Schedule> schedules;
    public int accountId;
    
    public Bus(String name, List<Facility> facility, Price price, int capacity, BusType busType, Station departure, Station arrival, int accountId) {
        this.facility = facility;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.busType = busType;
        this.departure = departure;
        this.arrival = arrival;
        this.schedules = new ArrayList<>();
        this.accountId = accountId;
    }
    
    public String toString() {
        return "Bus ID: " + this.id + "\tName: " + this.name + "\tFacility: " + this.facility + this.price + "\tCapacity: " + this.capacity + "\tBusType: " + this.busType + this.departure + this.arrival;
    }

    // TODO: Memastikan addSchedule method sudah benar
    public void addSchedule(Timestamp calendar) {
        for(Schedule s : this.schedules) {
            if(s.departureSchedule.equals(calendar)) {
                throw new IllegalArgumentException("Schedule with the same timestamp already exists.");
            }
        }
        this.schedules.add(new Schedule(calendar, capacity));
    }

    public boolean read(String file) {
        return false;
    }
    
    public Object write(){
        return null;
    }
}