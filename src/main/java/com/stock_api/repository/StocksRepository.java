package com.stock_api.repository;

import java.util.List;

import com.stock_api.model.Stocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {
    @Query(value = "select * from stocks s where askMin <> 0  order by updated_on desc fetch first 10 rows only", nativeQuery = true)
    List<Stocks> findStocks();

    @Query(value = "select * from stocks where stockName= ?1", nativeQuery = true)
    List<Stocks> findByName(String stockName);
}
