package auliaAnugrahAzizJBusRD;

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
        System.out.println(Thread.currentThread().getName() + " ID: " + Thread.currentThread().getId() + " is running");
        String msgSuccess = "Berhasil Melakukan Booking";
        String msgFailed = "Berhasil Melakukan Booking";
        System.out.println(Payment.makeBooking(this.timestamp, "RD01", this.bus)? msgSuccess : msgFailed);
    }
}
