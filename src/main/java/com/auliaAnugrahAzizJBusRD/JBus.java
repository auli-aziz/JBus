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
 * Main class for JBus
 *
 * @author Aulia Anugrah Aziz
 * @version 10 Desember 2023
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
