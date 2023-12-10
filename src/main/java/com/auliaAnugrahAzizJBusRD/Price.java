package com.auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Price here.
 *
 * @author Aulia Anugrah Aziz
 * @version 10 Desember 2023
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
        // this discount = 0;
        this.rebate = rebate;
    }
    
     public double getRebatedPrice() {
         if(rebate > price) {
             return 0;
         }
         return this.price - this.rebate;
     }
    
    public String toString() {
        return "\tRebate: " + this.rebate + "\tPrice: " + this.price;
    }
}
