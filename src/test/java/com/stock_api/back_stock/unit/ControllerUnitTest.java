package com.stock_api.back_stock.unit;

import com.stock_api.controller.StockController;
import com.stock_api.repository.ChartRepository;
import com.stock_api.repository.StocksRepository;
import com.stock_api.service.StocksService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

@WebMvcTest
@RequiredArgsConstructor
class ControllerUnitTest {
    @Autowired
    private StockController stockController;
    @MockBean
    private StocksRepository repository;
    @MockBean
    private ChartRepository chartRepository;
    @MockBean
    private StocksService service;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(this.stockController);
    }

    @Test
    void retornarStocks() {
        RestAssuredMockMvc.given().accept(ContentType.JSON).when()
                .get("/stocks")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void retornarAtualizados() {
        RestAssuredMockMvc.given().accept(ContentType.JSON).when()
                .get("/updated")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void RetornarStockPorId() {
        RestAssuredMockMvc.given().accept(ContentType.JSON).when()
                .get("/stocks/{id}", 72L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
