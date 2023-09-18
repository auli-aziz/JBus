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
        this.used = true;
        double discountPrice = price.price * (this.cut * 0.01);
        double returnVal1 = price.price - discountPrice;
        double returnVal2 = price.price - price.rebate;
        
        if(this.type == Type.DISCOUNT) {
            return returnVal1;
        } else if(this.type == Type.REBATE) {
            return returnVal2;
        } else {
            return 0;
        }
    }
}
