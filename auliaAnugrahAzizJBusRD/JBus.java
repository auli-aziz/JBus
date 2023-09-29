package auliaAnugrahAzizJBusRD;
import java.util.Calendar;


/**
 * Write a description of class JBus here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class JBus
{
    public static void main(String[] args) {
        
        Price[] unfilteredArray = new Price[5];
        for(int i = 0; i < unfilteredArray.length; i++) {
            int j = 5000;
            unfilteredArray[i] = new Price((i+1)*j);
        }
        
        System.out.println("Price List");
        for (Price price : unfilteredArray) {
            System.out.println(price.price);    
        }
        
        System.out.println("Below 12000.0");
        System.out.println(Validate.filter(unfilteredArray, 12000, true));
        System.out.println(Validate.filter(unfilteredArray, 10000, false));
        Bus testBus=createBus(); 
        Payment testPayment=new Payment(1,1,1,testBus.id,"S1"); 
        System.out.println(testPayment.getDepartureInfo()); 
        System.out.println(testPayment.getTime()); 
        Calendar schedule1=Calendar.getInstance(); 
        testBus.addSchedule(schedule1); 
        Calendar schedule2=Calendar.getInstance(); 
        schedule2.add(Calendar.DAY_OF_MONTH,3); 
        testBus.addSchedule(schedule2); 
        for(Schedule s: testBus.schedules){ 
            testBus.printSchedule(s); 
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
