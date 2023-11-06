package auliaAnugrahAzizJBusRD;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Write a description of class JBus here.
 *
 * @author Aulia Anugrah Aziz
 * @version (a version number or a date)
 */
public class JBus
{
    public static void main(String[] args) throws IOException, InterruptedException {

        // TP Modul 6
//        String filepath = "C:\\Users\\Aziz\\Documents\\Kuliah\\Semester 3\\OOP\\Java\\JBus\\data\\station.json";
//        Gson gson = new Gson();
//
//        try {
//            BufferedReader buffer = new BufferedReader(new FileReader(filepath));
//            List<Station> stationjson = gson.fromJson(buffer, new TypeToken<List<Station>>() {}.getType());
//            stationjson.forEach(e -> System.out.println(e.toString()));
//            System.out.println();
//            buffer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        // CS Modul 6
//        try{
//            String filepath2 = "C:\\Users\\Aziz\\Documents\\Kuliah\\Semester 3\\OOP\\Java\\JBus\\data\\buses_CS.json";
//            JsonTable<Bus> busList = new JsonTable<>(Bus.class, filepath2);
////            List<Bus> filteredBus = filterByDeparture(busList, City.JAKARTA, 1, 10);
////            List<Bus> filteredBus = filterByPrice(busList, 100000, 500000000);
////            System.out.println(filterBusId(busList, 155));
//            List<Bus> filteredBus = filterByDepartureAndArrival(busList, City.JAKARTA, City.SURABAYA, 0, 3);
//            filteredBus.forEach(bus -> System.out.println(bus.toString()));
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }

        // PT Modul 6
        String filepath3 = "C:\\Users\\Aziz\\Documents\\Kuliah\\Semester 3\\OOP\\Java\\JBus\\data\\accountDatabase.json";
        JsonTable<Account> tableAccount = new JsonTable<>(Account.class, filepath3);

        // Membuat beberapa objek Account dan menambahkannya ke JsonTable
        Account account1 = new Account("user1", "user1@example.com", "testing");
        Account account2 = new Account("user2", "user2@example.com", "testing");
        tableAccount.add(account1);
        tableAccount.add(account2);

        // Menulis data JsonTable ke file "accountDatabase.json"
        try (
            FileWriter fileWriter = new FileWriter(filepath3)) {

            Gson gson = new Gson();
            gson.toJson(tableAccount, fileWriter);
            for(Account a : tableAccount) {
                System.out.println("Account ID:" + a.id + " Name: " + a.name + " Email: " + a.email + " Password: " + a.password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bus bus = createBus();
        bus.schedules.forEach(e -> e.printSchedule(e));
        for(int i = 0; i < 10; i++) {
            BookingThread thread = new BookingThread("Thread " + i, bus, Timestamp.valueOf("2023-07-27 19:00:00"));
        }
        Thread.sleep(1000);
        bus.schedules.forEach(e -> e.printSchedule(e));


//        // PT Modul 5

//        // Tes Pagination
//        Bus b = createBus();
//        List<Timestamp> listOfSchedules = new ArrayList<>();
//        listOfSchedules.add(Timestamp.valueOf("2023-7-18 15:00:00"));
//        listOfSchedules.add(Timestamp.valueOf("2023-7-20 12:00:00"));
//        listOfSchedules.add(Timestamp.valueOf("2023-7-22 10:00:00"));
//        listOfSchedules.add(Timestamp.valueOf("2023-7-26 12:00:00"));
//
//        listOfSchedules.forEach(b::addSchedule);
//        System.out.println("Page 1");
//        Algorithm.paginate(b.schedules, 0, 3, t -> true).forEach(System.out::println);
//        System.out.println("=====================================================");
//        System.out.println("Page 2");
//        Algorithm.paginate(b.schedules, 1, 3, t -> true).forEach(System.out::println);
//        System.out.println("=====================================================");
//
//        // Tes Booking
//        String msgSuccess = "Booking Success!";
//        String msgFailed = "Booking Failed";
//        // invalid date, valid seat = Booking Failed
//        Timestamp t1 = Timestamp.valueOf("2023-7-19 15:00:00");
//        System.out.println("\nMake booking at July 19, 2023 15:00:00 Seats: RD17 RD18");
//        System.out.println(Payment.makeBooking(t1, List.of("RD17", "RD18"), b)? msgSuccess : msgFailed);
//        // valid date, invalid seat = Booking Failed
//        Timestamp t2 = Timestamp.valueOf("2023-7-18 15:00:00");
//        System.out.println("Make booking at July 18, 2023 15:00:00 Seat RD26");
//        System.out.println(Payment.makeBooking(t2, "RD26", b)? msgSuccess : msgFailed);
//        // valid date, valid seat = Booking Success
//        System.out.println("Make booking at July 18, 2023 15:00:00 Seats: RD07 RD08");
//        System.out.println(Payment.makeBooking(t2, List.of("RD07", "RD08"), b)? msgSuccess : msgFailed);
//        // valid date, valid seat = Booking Success
//        Timestamp t3 = Timestamp.valueOf("2023-7-20 12:00:00");
//        System.out.println("Make booking at July 20, 2023 12:00:00 Seats: RD01 RD02");
//        System.out.println(Payment.makeBooking(t3, List.of("RD01", "RD02"), b)? msgSuccess : msgFailed);
//        // valid date, book the same seat = Booking Failed
//        System.out.println("Make booking at July 20, 2023 12:00:00 Seat RD1");
//        System.out.println(Payment.makeBooking(t3, "RD01", b)? msgSuccess : msgFailed);
//        // check if the data changed
//        System.out.println("\nUpdated Schedule");
//        Algorithm.paginate(b.schedules, 0, 4, t-> true).forEach(System.out::println);
    }

    public static Bus createBus() {
        Price price = new Price(750000, 5);
        Bus bus = new Bus("Netlab Bus", Facility.LUNCH, price, 25, BusType.REGULER, City.BANDUNG, new Station("Depok Terminal", City.DEPOK, "Jl. Margonda Raya"), new Station("Halte UI", City.JAKARTA, "Universitas Indonesia"));
        Timestamp timestamp = Timestamp.valueOf("2023-07-27 19:00:00");
        bus.addSchedule(timestamp);
        return bus;
    }

    // TODO: Memastikan filterByDeparture method sudah benar
    public static List<Bus> filterByDeparture(List<Bus> buses, City departure, int page, int pageSize) {
        List<Bus> listOfBuses = new ArrayList<>();
        for(Bus b : buses) {
            if(b.departure.city == departure) {
                listOfBuses.add(b);
            }
        }
//        Predicate<Bus> predBus = (b) -> b.departure.city.equals(departure);
        return Algorithm.paginate(listOfBuses, page, pageSize, t -> true); // data yang sudah difilter (yang sesuai dengan departure)
    }

    public static List<Bus> filterByPrice(List<Bus> buses, int min, int max) {
//        List<Bus> temp = new ArrayList<>();
//        for(Bus b : buses) {
//            if(b.price.price >= min && b.price.price <= max) {
//                temp.add(b);
//            }
//        }
//        return temp;
        Predicate<Bus> predBus = (b) -> b.price.price >= min && b.price.price <= max;
        return Algorithm.collect(buses, predBus);
    }

    public static Bus filterBusId(List<Bus> buses, int id) {
//        Bus resultBus = null;
//        for(Bus b : buses) {
//            if(b.id == id) {
//                resultBus = b;
//            }
//        }
//        return resultBus;
        Predicate<Bus> predBus = (b) -> b.id == id;
        return Algorithm.find(buses, predBus);
    }

    public static List<Bus> filterByDepartureAndArrival(List<Bus> buses, City departure, City arrival, int page, int pageSize) {
//        List<Bus> temp = new ArrayList<>();
//        for(Bus b : buses) {
//            if(b.departure.city == departure && b.arrival.city == arrival) {
//                temp.add(b);
//            }
//        }
        Predicate<Bus> predBus = (b) -> b.departure.city.equals(departure) && b.arrival.city.equals(arrival);
        return Algorithm.paginate(buses, page, pageSize, predBus);
    }

//    private static void testExist(Integer[] t) {
//        int valueToCheck = 67;
//        boolean result3 = Algorithm.exists(t, valueToCheck);
//        if (result3) {
//            System.out.println(valueToCheck + " exist in the array.");
//        } else {
//            System.out.println(valueToCheck + " doesn't exists in the array.");
//        }
//    }
//    public static void testCount(Integer[] t) {
//        int valueToCount = 18;
//        int result = Algorithm.count(t, valueToCount);
//        System.out.println("Number " + valueToCount + " appears " + result + " times");
//    }
//    public static void testFind(Integer[] t) {
//        Integer valueToFind = 69;
//        Integer result2 = Algorithm.find(t, valueToFind);
//        System.out.print("Finding " + valueToFind + " inside the array : ");
//        if (result2 != null) {
//            System.out.println("Found!" + result2);
//        } else {
//            System.out.println("Not Found");
//        }
//    }
//    private static void testCollect(Integer[] t) {
//        Predicate<Integer> below = (val)->val<=22;
//        Predicate<Integer> above = (val)->val>43;
//
//        List<Integer> integerBelow = Algorithm.collect(t, below);
//        List<Integer> integerAbove = Algorithm.collect(t, above);
//
//        System.out.println("Below 22");
//        System.out.println(integerBelow);
//        System.out.println("Above 43");
//        System.out.println(integerAbove);
//    }

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
