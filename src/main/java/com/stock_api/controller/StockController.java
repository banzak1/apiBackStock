package com.stock_api.controller;

import com.stock_api.dto.StockDto;
import com.stock_api.model.Chart;
import com.stock_api.model.Stocks;
import com.stock_api.repository.ChartRepository;
import com.stock_api.repository.StocksRepository;
import com.stock_api.service.StocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@CrossOrigin(origins = { "http://localhost:8080/", "http://localhost:8082/" })
@RestController
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksService service;
    @Autowired
    private ChartRepository chartRepository;

    @GetMapping("/stocks/{id}")
    public Optional<Stocks> obterStock(@PathVariable(value = "id") Long id) {
        return stocksRepository.findById(id);
    }

    @GetMapping("/historico/{idStock}")
    public List<Chart> getChart(@PathVariable(value = "idStock") Stocks idStock) {
        return chartRepository.findByStock(idStock);
    }

    @GetMapping("/{stockName}")
    public ResponseEntity<List<Stocks>> getStocks(@PathVariable("stockName") String stockName) {
        try {
            return ResponseEntity.ok().body(service.getStock(stockName));
        } catch (Exception e) {
            if (e.getMessage().equals("FAZENDA_NOT_FOUND"))
                return ResponseEntity.notFound().build();
            return ResponseEntity.badRequest().build();
        }
    }

    private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/temporeal")
    public SseEmitter temporeal(HttpServletResponse response) {
        response.setHeader("Cache_Control", "no-store");
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            emitters.add(sseEmitter);
        } catch (Exception e) {
            logger.error("Nao deu certo o tempo real");
        }
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        return sseEmitter;
    }

    @GetMapping("/updated")
    public List<Stocks> listar10() {
        return stocksRepository.findStocks();
    }

    @GetMapping("/stocks")
    public List<Stocks> listar() {
        return stocksRepository.findAll();
    }

    @PostMapping("/teste")
    public ResponseEntity<Stocks> teste(@RequestBody StockDto stockDto) {
        Stocks stock = stocksRepository.findById(stockDto.getId()).orElseThrow();
        if (stockDto.getAskMax() != null) {
            stock.setAskMax(stockDto.getAskMax());
            stock.setAskMin(stockDto.getAskMin());
        }
        if (stockDto.getBidMin() != null) {
            stock.setBidMax(stockDto.getBidMax());
            stock.setBidMin(stockDto.getBidMin());
        }
        stock = stocksRepository.save(stock);
        publicar();
        atualizaPrices(stock);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    private void atualizaPrices(Stocks stocks) {
        Date date = new Date();
        Optional<Chart> historic2 = chartRepository.findByIdAndDate(stocks.getId(), new Timestamp(date.getTime()));

        if (historic2.isPresent()) {
            if (historic2.get().getHigh() < stocks.getAskMin()) {
                historic2.get().setHigh(stocks.getAskMin());
            }
            if (historic2.get().getLow() > stocks.getAskMin()) {
                historic2.get().setLow(stocks.getAskMin());
            }
            historic2.get().setFechado(stocks.getAskMin());
            chartRepository.save(historic2.get());
        } else if (stocks.getAskMin() == null) {
            logger.error("Não pode ser criado");
        } else {
            chartRepository.save(new Chart(stocks));
        }
    }

    public void publicar() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(stocksRepository.findStocks());
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

}