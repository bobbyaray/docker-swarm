package com.bobbyaray.cache;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Created by robertray on 6/7/17.
 */
@RedisHash("stockentry")
public class StockEntry {
    @Id
    private String symbol;
    private double currentPrice;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public StockEntry(String symbol, double currentPrice) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public StockEntry() {}

    @Override
    public String toString() {
        return "StockEntry{" +
                "symbol='" + symbol + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
