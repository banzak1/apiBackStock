package com.stock_api.service;

import com.stock_api.model.Stocks;
import com.stock_api.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StocksService {
    private final StocksRepository repository;

    public List<Stocks> getStock(String stockName) {
        return repository.findByName(stockName);

    }
}