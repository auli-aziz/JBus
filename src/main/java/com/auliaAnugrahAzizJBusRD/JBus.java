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

    public static List<Bus> filterByDeparture(List<Bus> buses, City departure, int page, int pageSize) {
        Predicate<Bus> predBus = (b) -> b.departure.city.equals(departure);
        List<Bus> listOfBuses = Algorithm.collect(buses, predBus);
        return Algorithm.paginate(listOfBuses, page, pageSize, t -> true); // data yang sudah difilter (yang sesuai dengan departure)
    }

    public static List<Bus> filterByPrice(List<Bus> buses, int min, int max) {
        Predicate<Bus> predBus = (b) -> b.price.price >= min && b.price.price <= max;
        return Algorithm.collect(buses, predBus);
    }

    public static Bus filterBusId(List<Bus> buses, int id) {
        Predicate<Bus> predBus = (b) -> b.id == id;
        return Algorithm.find(buses, predBus);
    }

    public static List<Bus> filterByDepartureAndArrival(List<Bus> buses, City departure, City arrival, int page, int pageSize) {
        Predicate<Bus> predBus = (b) -> b.departure.city.equals(departure) && b.arrival.city.equals(arrival);
        return Algorithm.paginate(buses, page, pageSize, predBus);
    }
    public static int getBusId() {
        return 0;
    }
    
    public static String getBusName() {
        return "Bus";
    }
    
    public static Boolean isDiscount() {
        return true;
    }
    
    public static int getOriginalPrice(int discountedPrice, float discountPercentage) {
        float discountDecimal = discountPercentage / 100;
        float originalPrice = discountedPrice / (1 - discountDecimal);
        return (int)originalPrice;
    }
    
    public static float getAdminFeePercentage() {
        return 0.02f;
    }
    
    public static double getAdminFee(double price) {
        float adminFeePercentage = getAdminFeePercentage();
        double adminFee = price * adminFeePercentage;
        return adminFee;
    }
    
    public static double getTotalPrice(double price, int numberOfSeat) {
        double totalPrice = price * numberOfSeat;
        double adminFee = getAdminFee(totalPrice);
        return totalPrice + adminFee;
    }
}
