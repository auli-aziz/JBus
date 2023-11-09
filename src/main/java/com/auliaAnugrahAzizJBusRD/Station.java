package com.auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Station here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Station extends Serializable
{
    public City city;
    public String stationName;
    public String address;
    
    public Station(String stationName, City city, String address) {
        this.stationName = stationName;
        this.city = city;
        this.address = address;
    }
    
    public String toString() {
        return " Station Id: " + Integer.toString(this.id) + "\tStation Name: " + this.stationName + "City: " + this.city + " Address: " + this.address;
    }
}
