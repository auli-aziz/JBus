package AuliaAnugrahAzizJBusRD;


/**
 * Write a description of class Review here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Review extends Serializable
{
    public String date;
    public String desc;
    
    public Review(int id, String date, String desc) {
        super(id);
        this.date = date;
        this.desc = desc;
    }
    
    public String toString() {
        return "Id: " + Integer.toString(this.id) + "\nDate: " + this.date + "\nDesc: " + this.desc;
    }
}
