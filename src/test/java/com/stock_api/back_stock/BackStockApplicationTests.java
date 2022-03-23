package com.stock_api.back_stock;

import java.util.List;

import com.stock_api.controller.StockController;
import com.stock_api.model.Stocks;
import com.stock_api.repository.ChartRepository;
import com.stock_api.repository.StocksRepository;
import com.stock_api.service.StocksService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
class BackStockApplicationTests {

	@InjectMocks
	private StockController stockController;
	@Mock
	private StocksRepository repository;
	@Mock
	private StocksService service;
	@Mock
	private ChartRepository graficoRepository;

	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.standaloneSetup(this.stockController);
	}

	@Test
	void teste() {
		List<Stocks> stocks = repository.findAll();
		Assertions.assertThat(stocks).isNotNull();
	}

}
