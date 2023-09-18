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
        double discountPrice = price.price * (this.cut * 0.01);
        double returnVal1 = (this.cut < price.price) ? price.price - discountPrice : 0;
        double returnVal2 = (this.cut < price.price) ? price.price - this.cut : 0;
        boolean state = canApply(price);
        
        if(state && this.type == Type.DISCOUNT) {
            this.used = true;
            return returnVal1;
        } else if(state && this.type == Type.REBATE) {
            this.used = true;
            return returnVal2;
        } else {            
            this.used = true;
            return price.price;
        }
    }
}