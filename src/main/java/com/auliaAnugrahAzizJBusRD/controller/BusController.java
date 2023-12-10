package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.*;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Handles all the API requests that is related to manipulating data on bus.json
 *
 * @author Aulia Anugrah Aziz
 * @version 10 December 2023
 */
@RestController
@RequestMapping("/bus")
public class BusController implements BasicGetController<Bus> {
    public static @JsonAutowired(value = Bus.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\bus.json") JsonTable<Bus> busTable;

    public BusController() {}

    public JsonTable<Bus> getJsonTable() {
        return this.busTable;
    }

    /**
     * creates a new bus for an account registered as renter
     *
     * @param accountId             account ID that will create a new bus
     * @param name                  name of the new bus
     * @param capacity              capacity of the new bus
     * @param facilities            facilities of the new bus
     * @param busType               bus type of the new bus
     * @param price                 price of the new bus
     * @param stationDepartureId    corresponding station ID in station.json for departure
     * @param stationArrivalId      corresponding station ID in station.json for arrival
     * @return                      <code>new BaseResponse<>(true, "Add Bus Successful", bus)</code>
     *                              if account, departure station and arrival station exist
     *                              <code>new BaseResponse<>(false, "Add Bus Failed", null)</code>
     *                              if account, departure station and arrival station does not exist
     */
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
        boolean isAccountExist = Algorithm.exists(AccountController.accountTable, predAcc);
        boolean isDepStatExist = Algorithm.exists(StationController.stationTable, predDepStation);
        boolean isArrStatExist = Algorithm.exists(StationController.stationTable, predArrStation);

        if(isAccountExist && isDepStatExist && isArrStatExist) {
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

    /**
     * creates new schedule for the given bus ID
     *
     * @param busId bus ID
     * @param time  schedule time for the given bus ID
     * @return      <code>new BaseResponse<>(true, "Add Schedule Successful", b)</code>
     *              if bus with the given ID is found
     *              <code>BaseResponse<>(false, "Add Schedule Failed", null)</code>
     *              if bus with the given ID is not found
     */
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

    /**
     *
     * @return getJsonTable() - all bus in the bus.json database
     */
    @GetMapping("/getAll")
    public List<Bus> getAllBus() { return getJsonTable(); }

    /**
     *
     * @param accountId     account ID to find bus
     * @return              all the bus that matches the accountId
     */
    @GetMapping("/getMyBus")
    public List<Bus> getMyBus(@RequestParam int accountId) {
        return Algorithm.<Bus>collect(getJsonTable(), b -> b.accountId == accountId);
    }

    /**
     *
     * @param busId     bus ID to find the matching object
     * @return          bus object to display bus detail
     */
    @GetMapping("/getBusDetails")
    public Bus getBusDetails(@RequestParam int busId) {
        Predicate<Bus> pred = b -> b.id == busId;
        return Algorithm.find(getJsonTable(), pred);
    }
}
