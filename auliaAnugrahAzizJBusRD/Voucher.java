package auliaAnugrahAzizJBusRD;


/**
 * Write a description of class Voucher here.
 *
 * @Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Voucher extends Serializable implements FileParser
{
    public String name;
    private boolean used;
    public double minimum;
    public double cut;
    public int code;
    public Type type;
    
    public Voucher(int id, String name, int code, Type type, double minimum, double cut) {
        super(id);
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
    
    
    public Object write() {
        return null;
    }
    
    public boolean read(String file) {
        return false;
    }
}