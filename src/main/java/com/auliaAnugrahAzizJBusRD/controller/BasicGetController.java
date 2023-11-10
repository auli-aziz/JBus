package com.auliaAnugrahAzizJBusRD.controller;

import com.auliaAnugrahAzizJBusRD.Algorithm;
import com.auliaAnugrahAzizJBusRD.Predicate;
import com.auliaAnugrahAzizJBusRD.dbjson.JsonTable;
import com.auliaAnugrahAzizJBusRD.dbjson.Serializable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/account")
public interface BasicGetController <T extends Serializable> {
    public abstract <T extends Serializable> JsonTable<T> getJsonTable();
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public default <T extends Serializable> List<T> getPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize
            ) {
        return Algorithm.<T>paginate(getJsonTable(), page, pageSize, t -> true);
    }

    @GetMapping("/{id}")
    public default T getById(@PathVariable int id) {
        Predicate<T> pred = t -> t.id == id;
        return Algorithm.<T>find(getJsonTable(), pred);
    }
}
