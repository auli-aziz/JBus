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
        System.out.println("getDiscountedPrice = " + getDiscountedPrice(1000, 10.0f));
        System.out.println("getOriginalPrice = " + getOriginalPrice(900, 10.0f));
        System.out.println("getAdminFeePercentage = " + getAdminFeePercentage());
        System.out.println("getAdminFee = " + getAdminFee(1000));
        System.out.println("getTotalPrice = " + getTotalPrice(10000, 2));
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
            return ((beforeDiscount - afterDiscount) / beforeDiscount) * 100;
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
        return discountedPrice / (1 - (int)discountDecimal);
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
