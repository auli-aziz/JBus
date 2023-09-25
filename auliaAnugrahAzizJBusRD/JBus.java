package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class JBus here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class JBus
{
    public static void main(String[] args) {
        //Bus testBus = createBus();
        //System.out.println(testBus.name);
        //System.out.println(testBus.facility);
        //System.out.println(testBus.price.price);
        //System.out.println(testBus.capacity);
        
        //Rating rate = new Rating();
        //System.out.println(rate.getAverage());
        //rate.insert(10);
        //System.out.println(rate.getAverage());
        //System.out.println(rate.getCount());
        //System.out.println(rate.getTotal());
        
        Payment testPayment = new Payment(1, 1, 1, "A", 1, "A", "A");
        Invoice testInvoice = new Invoice(2,2,2, "B");
        Station testStation = new Station(3, "C", City.DEPOK);
        Account testAccount = new Account(12 ,"Hello", "Hello2", "Hello3");
        Renter testRenter = new Renter(101, "test", 81);
        Payment testPayment2 = new Payment(1, testAccount, testRenter, "A", 1, "A", "A");
        Invoice testInvoice2 = new Invoice(2,testAccount,testRenter, "B");
        System.out.println(testPayment.toString());
        System.out.println(testInvoice.toString());
        System.out.println(testStation.toString());
        System.out.println(testPayment2.toString());
        System.out.println(testInvoice2.toString());
    }
    
    //public static Bus createBus() {
    //    Price price = new Price(750000, 5);
    //    Bus bus = new Bus(101, "Netlab Bus", Facility.LUNCH, price, 25);
    //    return bus;
    //}

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
