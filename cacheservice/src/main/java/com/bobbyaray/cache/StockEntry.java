package com.bobbyaray.cache;

/**
 * Created by robertray on 6/7/17.
 */
public class StockEntry {
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
}
