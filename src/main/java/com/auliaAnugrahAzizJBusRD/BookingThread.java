package com.auliaAnugrahAzizJBusRD;

import java.sql.Timestamp;

public class BookingThread extends Thread {
    private Bus bus;
    private Timestamp timestamp;
    public BookingThread(String name, Bus bus, Timestamp timestamp) {
        super(name);
        this.bus = bus;
        this.timestamp = timestamp;
        this.start();
    }

    public void run() {
        synchronized (this.bus) {
            System.out.println(Thread.currentThread().getName() + " ID: " + Thread.currentThread().getId() + " is running");
            String msgSuccess = "Berhasil Melakukan Booking";
            String msgFailed = "Berhasil Melakukan Booking";
            if(Payment.makeBooking(this.timestamp, "RD01", this.bus)) {
                System.out.println(msgSuccess);
            } else {
                System.out.println(msgFailed);
            }
        }
    }
}
