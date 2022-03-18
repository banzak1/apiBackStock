package com.stock_api.controller;

import com.stock_api.model.Chart;
import com.stock_api.model.Stocks;
import com.stock_api.repository.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
public class ChartController {
    @Autowired
    private ChartRepository repository;

    @GetMapping("/historico")
    public List<Chart> getChart(@PathVariable(value = "id_stock") Stocks idStock) {
        return repository.findByStock(idStock);
    }
}
