package AuliaAnugrahAzizJBusRD;


import java.util.HashMap;

/**
 * Write a description of class Serializable here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class Serializable {
    public final int id;
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<>();
    
    protected Serializable(){
        if(mapCounter == null) {
            this.id = 0;
        } else {
            int serial = mapCounter.getOrDefault(this.getClass(), 0);
            mapCounter.put(this.getClass(), serial + 1);
            this.id = serial;
        }

//        this.id = mapCounter.getOrDefault(this.getClass(), 0) + 1;
//        this.mapCounter.put(getClass(), this.id);
    }

    public static <T> Integer getLastAssignedId(Class<T> mapCounter) {
        return Serializable.mapCounter.getOrDefault(mapCounter, 0);
    }

    public static <T> Integer setLastAssignedId(Class<T> mapCounter, int id) {
        return Serializable.mapCounter.put(mapCounter, id);
    }

    public int compareTo(Serializable serializable) {
//        return Integer.compare(this.id, serializable.id);
        if(this.id == serializable.id) {
            return 0;
        } else if(this.id > serializable.id) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean equals(Serializable serializable) {
        if(serializable.id == this.id) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Serializable) {
            Serializable s = (Serializable) obj;
            if(s.id == this.id) {
                return true;
            }
        }
        return false;
    }
}
