package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Voucher here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Voucher
{
    public String name;
    private boolean used;
    public double minimum;
    public double cut;
    public int code;
    public Type type;
    
    public static void main(String[] args) {
        Voucher voucher1 = new Voucher("voucher 1", 11, Type.DISCOUNT, 90, 110);
        Voucher voucher2 = new Voucher("voucher 1", 11, Type.DISCOUNT, 90, 90);
        Price item1 = new Price(100, 10);
        
        System.out.println(voucher1.canApply(item1));
        System.out.println(voucher1.apply(item1));
        System.out.println(voucher1.canApply(item1));
        System.out.println(voucher1.apply(item1));
        
        System.out.println(voucher2.canApply(item1));
        System.out.println(voucher2.apply(item1));
        System.out.println(voucher2.canApply(item1));
        System.out.println(voucher2.apply(item1));
    }
    
    public Voucher(String name, int code, Type type, double minimum, double cut) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.minimum = minimum;
        this.cut = cut;
        this.used = false;
    }
    
    public boolean isUsed() {
        return this.used;
    }
    
    public boolean canApply(Price price) {
        if(price.price > this.minimum && this.used == false) {
            return true;
        }
        return false;
    }
    
    public double apply(Price price) {
        double discountPrice = price.price * (this.cut * 0.01);
        double returnVal1 = (this.cut < 100) ? price.price - discountPrice : 0;
        double returnVal2 = (this.cut < price.price) ? price.price - this.cut : 0;
        boolean state = canApply(price);
        this.used = true;
        if(state && this.type == Type.DISCOUNT) {
            return returnVal1;
        } else if(state && this.type == Type.REBATE) {
            return returnVal2;
        } else {            
            return price.price;
        }
    }
}