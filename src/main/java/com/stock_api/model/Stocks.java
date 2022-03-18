package com.stock_api.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "stocks")
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockSymbol;
    private String stockName;
    private Double askMin;
    private Double askMax;
    private Double bidMin;
    private Double bidMax;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public Double getAskMin() {
        return askMin;
    }

    public void setAskMin(Double askMin) {
        this.askMin = askMin;
    }

    public Double getAskMax() {
        return askMax;
    }

    public void setAskMax(Double askMax) {
        this.askMax = askMax;
    }

    public Double getBidMin() {
        return bidMin;
    }

    public void setBidMin(Double bidMin) {
        this.bidMin = bidMin;
    }

    public Double getBidMax() {
        return bidMax;
    }

    public void setBidMax(Double bidMax) {
        this.bidMax = bidMax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + ((id == null) ? 0 : id.hashCode());

    }

    static final int RESULT = 0;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Stocks other = (Stocks) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;

    }

    public Stocks() {
        this.createdOn = Timestamp.valueOf(LocalDateTime.now());
        this.updatedOn = Timestamp.valueOf(LocalDateTime.now());
    }
}
