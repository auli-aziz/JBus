package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Rating here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rating
{
    private long count;
    private long total;
    
    public Rating() {
        this.count = 0;
        this.total = 0;
    }
    
    public void insert(int rating) {
        this.total += rating;
        this.count++;
    }
    
    public double getAverage() {
        if(count == 0) {
            return 0;
        }
        double average = ((double)total) / ((double)count);
        return average;
    }
    
    public long getCount() {
        return this.count;
    }
    
    public long getTotal() {
        return this.total;
    }
    
    public String toString() {
        return "Count: " + this.count + "\nTotal: " + this.total;
    }
}
