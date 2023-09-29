package auliaAnugrahAzizJBusRD;

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
        ArrayList<Double> temp2 = new ArrayList<>();
        
        for(int i = 0; i < list.length; i++) {
            temp.add(list[i].price);
        }
        
        if(less) {
            for(int i = 0; i < list.length; i++) {
                if(temp.get(i) <= value) {
                    temp2.add(temp.get(i));
                }
            }
        } else {
            for(int i = 0; i < list.length; i++) {
                if(temp.get(i) > value) {
                    temp2.add(temp.get(i));
                }
            }
        }
        return temp2;
    }
}
