package com.stock_api.controller;

import java.util.List;
import java.util.Optional;

import com.stock_api.model.Stocks;
import com.stock_api.repository.StocksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class StockController {

    @Autowired
    private StocksRepository stockRepository;

    @GetMapping("/stocks/{id}")
    public Optional<Stocks> obterStock(@PathVariable(value = "id") Long id) {
        return stockRepository.findById(id);
    }

    @GetMapping("/stocks")
    public List<Stocks> listar() {
        return stockRepository.findAll();
    }

    @PostMapping("/stocks")
    public Stocks adicionar(@RequestBody Stocks stock) {
        return stockRepository.save(stock);

    }
}