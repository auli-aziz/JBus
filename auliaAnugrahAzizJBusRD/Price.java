package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Price here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Price
{
    public double rebate;
    public double price;
    
    public Price(double price){
        this.price = price;
        this.rebate = 0;
    }
    
    public Price(double price, double rebate){
        this.price = price;
        this.rebate = rebate;
    }
}
