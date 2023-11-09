package com.auliaAnugrahAzizJBusRD;

import java.util.ArrayList;

/**
 * Write a description of class Validate here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Validate
{
    public Validate() {
        
    }
    
    public static ArrayList filter(Price[] list, int value, boolean less) {
        ArrayList<Double> temp = new ArrayList<>();
        
        if(less) {
            for(Price iterator : list) {
                if(iterator.price <= value) {
                    temp.add(iterator.price);
                }
            }
        } else {
            for(Price iterator : list) {
                if(iterator.price > value) {
                    temp.add(iterator.price);
                }
            }
        }
        return temp;
    }
}
