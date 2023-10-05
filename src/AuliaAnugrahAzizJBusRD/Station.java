package AuliaAnugrahAzizJBusRD;


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
    
    public Station(int id, String stationName, City city, String address) {
        super(id);
        this.stationName = stationName;
        this.city = city;
        this.address = address;
    }
    
    public String toString() {
        return "\nId: " + Integer.toString(this.id) + "\nStationName: " + this.stationName + "\nCity " + this.city + "\nAddress " + this.address;
    }
}
