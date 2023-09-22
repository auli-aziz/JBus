package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Station here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Station extends Serializable
{
    public City city;
    public String stationName;
    
    public Station(int id, String stationName, City city) {
        super(id);
        this.stationName = stationName;
        this.city = city;
    }
    
    public String print() {
        return Integer.toString(this.id) + " " + this.stationName + " " + this.city;
    }
}
