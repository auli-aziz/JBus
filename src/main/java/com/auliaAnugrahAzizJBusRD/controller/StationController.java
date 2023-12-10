package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.Bus;
import com.auliaAnugrahAzizJBusRD.City;
import com.auliaAnugrahAzizJBusRD.Station;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonAutowired;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles all the API requests that is related to manipulating data on station.json
 *
 * @author Netlab Team
 * @version 0.1
 */
@RestController
@RequestMapping("/station")
public class StationController implements BasicGetController<Station> {
    public static @JsonAutowired(value = Station.class, filepath = "src\\main\\java\\com\\auliaAnugrahAzizJBusRD\\json\\station.json") JsonTable<Station> stationTable;

    public StationController() {

    }

    @Override
    public JsonTable<Station> getJsonTable() {
        return this.stationTable;
    }

    /**
     * adds new station to station.json
     *
     * @param stationName   new station name
     * @param city          city enum
     * @param address       new station address
     * @return              <code>new BaseResponse<>(true, "Station added successfully", newStation)</code>
     *                      if station is added to json table
     *                      <code>new BaseResponse<>(false, "Invalid city value", null)</code>
     *                      if the city parameter is invalid
     *                      <code>new BaseResponse<>(false, "An error occurred while adding the station", null)</code>
     *                      if a exception occurs
     * @see                 City
     */
    @PostMapping("/create")
    public BaseResponse<Station> createStation(
            @RequestParam String stationName,
            @RequestParam String city,
            @RequestParam String address
    ) {
        try {
            // Validate parameters
            if (stationName.isBlank() || city.isBlank() || address.isBlank()) {
                return new BaseResponse<>(false, "Parameter values cannot be blank or null", null);
            }

            // Validate city as a valid enum value
            City cityEnum = City.valueOf(city.toUpperCase());

            // Create a new station using the provided details
            Station newStation = new Station(stationName, cityEnum, address);

            // Add the new station to the stationTable
            stationTable.add(newStation);

            //Success response message
            return new BaseResponse<>(true, "Station added successfully", newStation);
        } catch (IllegalArgumentException e) {
            // Handle invalid enum value
            return new BaseResponse<>(false, "Invalid city value", null);
        } catch (Exception e) {
            // Handle other unexpected errors
            return new BaseResponse<>(false, "An error occurred while adding the station", null);
        }
    }

    /**
     *
     * @return      all the station in station.json
     */
    @GetMapping("/getAll")
    public List<Station> getAllStation() { return getJsonTable(); }
}
