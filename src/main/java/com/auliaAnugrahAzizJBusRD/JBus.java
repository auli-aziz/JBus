package com.auliaAnugrahAzizJBusRD;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.auliaAnugrahAzizJBusRD.dbjson.JsonDBEngine;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Write a description of class JBus here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
@SpringBootApplication
public class JBus
{
    public static void main(String[] args)
    {
        JsonDBEngine.Run(JBus.class);
        SpringApplication.run(JBus.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> JsonDBEngine.join()));
    }

    public static Bus createBus() {
        Price price = new Price(750000, 5);
        Bus bus = new Bus("Netlab Bus", Facility.LUNCH, price, 25, BusType.REGULER, City.BANDUNG, new Station("Depok Terminal", City.DEPOK, "Jl. Margonda Raya"), new Station("Halte UI", City.JAKARTA, "Universitas Indonesia"));
        Timestamp timestamp = Timestamp.valueOf("2023-07-27 19:00:00");
        bus.addSchedule(timestamp);
        return bus;
    }

    // TODO: Memastikan filterByDeparture method sudah benar
    public static List<Bus> filterByDeparture(List<Bus> buses, City departure, int page, int pageSize) {
        List<Bus> listOfBuses = new ArrayList<>();
        for(Bus b : buses) {
            if(b.departure.city == departure) {
                listOfBuses.add(b);
            }
        }
//        Predicate<Bus> predBus = (b) -> b.departure.city.equals(departure);
        return Algorithm.paginate(listOfBuses, page, pageSize, t -> true); // data yang sudah difilter (yang sesuai dengan departure)
    }

    public static List<Bus> filterByPrice(List<Bus> buses, int min, int max) {
//        List<Bus> temp = new ArrayList<>();
//        for(Bus b : buses) {
//            if(b.price.price >= min && b.price.price <= max) {
//                temp.add(b);
//            }
//        }
//        return temp;
        Predicate<Bus> predBus = (b) -> b.price.price >= min && b.price.price <= max;
        return Algorithm.collect(buses, predBus);
    }

    public static Bus filterBusId(List<Bus> buses, int id) {
//        Bus resultBus = null;
//        for(Bus b : buses) {
//            if(b.id == id) {
//                resultBus = b;
//            }
//        }
//        return resultBus;
        Predicate<Bus> predBus = (b) -> b.id == id;
        return Algorithm.find(buses, predBus);
    }

    public static List<Bus> filterByDepartureAndArrival(List<Bus> buses, City departure, City arrival, int page, int pageSize) {
//        List<Bus> temp = new ArrayList<>();
//        for(Bus b : buses) {
//            if(b.departure.city == departure && b.arrival.city == arrival) {
//                temp.add(b);
//            }
//        }
        Predicate<Bus> predBus = (b) -> b.departure.city.equals(departure) && b.arrival.city.equals(arrival);
        return Algorithm.paginate(buses, page, pageSize, predBus);
    }

//    private static void testExist(Integer[] t) {
//        int valueToCheck = 67;
//        boolean result3 = Algorithm.exists(t, valueToCheck);
//        if (result3) {
//            System.out.println(valueToCheck + " exist in the array.");
//        } else {
//            System.out.println(valueToCheck + " doesn't exists in the array.");
//        }
//    }
//    public static void testCount(Integer[] t) {
//        int valueToCount = 18;
//        int result = Algorithm.count(t, valueToCount);
//        System.out.println("Number " + valueToCount + " appears " + result + " times");
//    }
//    public static void testFind(Integer[] t) {
//        Integer valueToFind = 69;
//        Integer result2 = Algorithm.find(t, valueToFind);
//        System.out.print("Finding " + valueToFind + " inside the array : ");
//        if (result2 != null) {
//            System.out.println("Found!" + result2);
//        } else {
//            System.out.println("Not Found");
//        }
//    }
//    private static void testCollect(Integer[] t) {
//        Predicate<Integer> below = (val)->val<=22;
//        Predicate<Integer> above = (val)->val>43;
//
//        List<Integer> integerBelow = Algorithm.collect(t, below);
//        List<Integer> integerAbove = Algorithm.collect(t, above);
//
//        System.out.println("Below 22");
//        System.out.println(integerBelow);
//        System.out.println("Above 43");
//        System.out.println(integerAbove);
//    }

    public static int getBusId() {
        return 0;
    }
    
    public static String getBusName() {
        return "Bus";
    }
    
    public static Boolean isDiscount() {
        return true;
    }
    
    public static float getDiscountPercentage(int beforeDiscount, int afterDiscount){
        if(beforeDiscount > afterDiscount) {
            return (((float)beforeDiscount - (float)afterDiscount) / (float)beforeDiscount) * 100;
        } else {
            return 0.0f;   
        }
    } 
    
    public static int getDiscountedPrice(int price, float discountPercentage) {
        if(discountPercentage > 100.0f) {
            discountPercentage = 100.0f;
        }
        float discountedPrice = price - (price * discountPercentage) / 100;
        return (int)discountedPrice;
    }
    
    public static int getOriginalPrice(int discountedPrice, float discountPercentage) {
        float discountDecimal = discountPercentage / 100;
        float originalPrice = discountedPrice / (1 - discountDecimal);
        return (int)originalPrice;
    }
    
    public static float getAdminFeePercentage() {
        return 0.05f;
    }
    
    public static int getAdminFee(int price) {
        float adminFeePercentage = getAdminFeePercentage();
        float adminFee = price * adminFeePercentage;
        return (int)adminFee;
    }
    
    public static int getTotalPrice(int price, int numberOfSeat) {
        int totalPrice = price * numberOfSeat;
        int adminFee = getAdminFee(totalPrice);
        return (totalPrice) + adminFee;
    }
}
