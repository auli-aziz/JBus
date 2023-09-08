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
        System.out.println(getBusId());
        System.out.println(getBusName());
        System.out.println(isDiscount());
        System.out.println("getDiscountPercentage = " + getDiscountPercentage(1000, 900));
        System.out.println("getDiscountPercentage = " + getDiscountPercentage(1000, 0));
        System.out.println("getDiscountPercentage = " + getDiscountPercentage(0, 0));
        System.out.println("getDiscountedPrice = " + getDiscountedPrice(1000, 10.0f));
        System.out.println("getDiscountedPrice = " + getDiscountedPrice(1000, 100.0f));
        System.out.println("getDiscountedPrice = " + getDiscountedPrice(1000, 120.0f));
        System.out.println("getDiscountedPrice = " + getDiscountedPrice(0, 0.0f));
        System.out.println("getOriginalPrice = " + getOriginalPrice(900, 10.0f));
        System.out.println("getOriginalPrice = " + getOriginalPrice(1000, 0.0f));
        System.out.println("getOriginalPrice = " + getOriginalPrice(0, 100.0f));
        System.out.println("getOriginalPrice = " + getOriginalPrice(0, 120.0f));
        System.out.println("getAdminFeePercentage = " + getAdminFeePercentage());
        System.out.println("getAdminFee = " + getAdminFee(1000));
        System.out.println("getAdminFee = " + getAdminFee(500));
        System.out.println("getAdminFee = " + getAdminFee(0));
        System.out.println("getTotalPrice = " + getTotalPrice(10000, 2));
        System.out.println("getTotalPrice = " + getTotalPrice(5000, 1));
        System.out.println("getTotalPrice = " + getTotalPrice(0, 2));
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
        return price - (price * (int)discountPercentage) / 100;
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
