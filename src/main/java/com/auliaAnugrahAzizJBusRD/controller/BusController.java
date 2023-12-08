package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.*;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/bus")
public class BusController implements BasicGetController<Bus> {
    public static @JsonAutowired(value = Bus.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\bus.json") JsonTable<Bus> busTable;

    public BusController() {}

    public JsonTable<Bus> getJsonTable() {
        return this.busTable;
    }

    @PostMapping("/create")
    public BaseResponse<Bus> create(
            @RequestParam int accountId,
            @RequestParam String name,
            @RequestParam int capacity,
            @RequestParam List<Facility> facilities,
            @RequestParam BusType busType,
            @RequestParam double price,
            @RequestParam int stationDepartureId,
            @RequestParam int stationArrivalId
    ) {
        Predicate<Account> predAcc = a -> a.id == accountId && a.company != null;
        Predicate<Station> predDepStation = s -> s.id == stationDepartureId;
        Predicate<Station> predArrStation = s -> s.id == stationArrivalId;
        boolean valid1 = Algorithm.exists(AccountController.accountTable, predAcc);
        boolean valid2 = Algorithm.exists(StationController.stationTable, predDepStation);
        boolean valid3 = Algorithm.exists(StationController.stationTable, predArrStation);

        if(valid1 && valid2 && valid3) {
            Station departure = Algorithm.find(StationController.stationTable, predDepStation);
            Station arrival = Algorithm.find(StationController.stationTable, predArrStation);
            Price p = new Price(price, price * 0.25);
            Bus bus = new Bus(name, facilities, p, capacity, busType, departure, arrival, accountId);
            busTable.add(bus);
            return new BaseResponse<>(true, "Add Bus Successful", bus);
        } else {
            return new BaseResponse<>(false, "Add Bus Failed", null);
        }
    }

    @PostMapping("/addSchedule")
    public BaseResponse<Bus> addSchedule(
            @RequestParam int busId,
            @RequestParam String time
    ) {
        Predicate<Bus> pred = b -> b.id == busId;
        Bus b = Algorithm.find(this.busTable, pred);
        if(b != null) {
            Timestamp timestamp = Timestamp.valueOf(time);
            b.addSchedule(timestamp);
            return new BaseResponse<>(true, "Add Schedule Successful", b);
        }
        return new BaseResponse<>(false, "Add Schedule Failed", null);
    }

    @GetMapping("/getAll")
    public List<Bus> getAllBus() {
        return getJsonTable();
    }

    @GetMapping("/getMyBus")
    public List<Bus> getMyBus(@RequestParam int accountId) {
        return Algorithm.<Bus>collect(getJsonTable(), b -> b.accountId == accountId);
    }

    @GetMapping("/getBusDetails")
    public Bus getBusDetails(@RequestParam int busId) {
        Predicate<Bus> pred = b -> b.id == busId;
        return Algorithm.find(getJsonTable(), pred);
    }
}
