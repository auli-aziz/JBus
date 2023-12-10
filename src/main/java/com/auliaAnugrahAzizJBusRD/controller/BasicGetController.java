package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.Algorithm;
import com.auliaAnugrahAzizJBusRD.Predicate;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * interface with default method of getById and getPage
 *
 * @author Aulia Anugrah Aziz
 * @version 10 December 2023
 */
@RestController
@RequestMapping("/account")
public interface BasicGetController <T extends Serializable> {
    public abstract <T extends Serializable> JsonTable<T> getJsonTable();

    /**
     *
     * @param page      current displayed page
     * @param pageSize  desired page size (the elements displayed in a single page)
     * @return          <code>Algorithm.<T>paginate(getJsonTable(), page, pageSize, t -> true)</code>
     * @param <T>       generic object
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public default <T extends Serializable> List<T> getPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize
            ) {
        return Algorithm.<T>paginate(getJsonTable(), page, pageSize, t -> true);
    }

    /**
     *
     * @param id    ID of the object
     * @return      an object that matches ID
     * @see         Algorithm#find(Iterable, Predicate)
     */
    @GetMapping("/{id}")
    public default T getById(@PathVariable int id) {
        Predicate<T> pred = t -> t.id == id;
        return Algorithm.<T>find(getJsonTable(), pred);
    }
}
