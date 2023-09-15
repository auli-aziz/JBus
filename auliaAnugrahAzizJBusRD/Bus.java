package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Bus here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bus
{
    public int capacity;
    public Facility facility;
    public String name;
    public Price price;
    
    public Bus(String name, Facility facility, Price price, int capacity) {
        this.capacity = capacity;
        this.facility = facility;
        this.name = name;
        this.price = price;
    }
}
