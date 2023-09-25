package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Bus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bus extends Serializable
{
    public int capacity;
    public Facility facility;
    public String name;
    public Price price;
    public Station departure;
    public Station arrival;
    public BusType busType;
    public City city;
    
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
    }
    
    public String toString() {
        String returnVal = "Id: " + this.id + "\nName: " + this.name + "\nFacility: " + this.facility + "\nPrice: " + this.price + "\nCapacity: " + this.capacity + "\nBusType: " + this.busType + "\nCity: " + this.city + "\nDeparture: " + this.departure + "\nArrival: " + this.arrival;
        return returnVal;
    }
}