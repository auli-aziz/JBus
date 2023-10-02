package auliaAnugrahAzizJBusRD;

import java.sql.Timestamp;

/**
 * Write a description of class JBus here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class JBus
{
    public static void main(String[] args) {
        Bus b = createBus();
        Timestamp schedule1 = Timestamp.valueOf("2023-7-18 15:00:00");
        Timestamp schedule2 = Timestamp.valueOf("2023-7-20 12:00:00");
        
        b.addSchedule(schedule1);
        b.addSchedule(schedule2);
        
        for(Schedule s: b.schedules){
            s.printSchedule(s);
        }
        
        // Invalide date
        Timestamp t1 = Timestamp.valueOf("2023-7-19 15:00:00");
        System.out.println("\nMake booking at July 19, 2023 15:00:00 seat RD12");
        System.out.print(Payment.makeBooking(t1, "RD12", b));
        
        // Valid date, invalid seat
        Timestamp t2 = Timestamp.valueOf("2023-7-18 15:00:00");
        System.out.println("\nMake booking at July 18, 2023 15:00:00 seat RD20");
        System.out.print(Payment.makeBooking(t2, "RD20", b));
        
        // Valid date, valid seat
        System.out.println("\nMake booking at July 18, 2023 15:00:00 seat RD07");
        System.out.print(Payment.makeBooking(t2, "RD07", b));
        
        Timestamp t3 = Timestamp.valueOf("2023-7-20 12:00:00");
        System.out.println("\nMake booking at July 20, 2023 12:00:00 seat RD01");
        System.out.print(Payment.makeBooking(t3, "RD01", b));
        
        System.out.println("\nMake booking at July 20, 2023 12:00:00 seat RD01 again");
        System.out.print(Payment.makeBooking(t3, "RD01", b));
        
        System.out.println("\nUpdated Schedule\n");
        for(Schedule s: b.schedules){
            s.printSchedule(s);
        }
    }
    
    public static Bus createBus() {
        Price price = new Price(750000, 5);
        Bus bus = new Bus(1, "Netlab Bus", Facility.LUNCH, price, 25, BusType.REGULER, City.BANDUNG, new Station(1, "Depok Terminal", City.DEPOK, "Jl. Margonda Raya"), new Station(2, "Halte UI", City.JAKARTA, "Universitas Indonesia"));
        return bus;
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
