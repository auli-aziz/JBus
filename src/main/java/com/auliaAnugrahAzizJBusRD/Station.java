package com.auliaAnugrahAzizJBusRD;


import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;

/**
 * Station is used for the departure and arrival station of the bus
 *
 * @author Aulia Anugrah Aziz
 * @version 10 Desember 2023
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
