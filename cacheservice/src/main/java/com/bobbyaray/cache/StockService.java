package com.bobbyaray.cache;

import java.util.List;

/**
 * Created by robertray on 6/29/17.
 */
public interface StockService {
    void insertStockQuote(StockEntry stockEntry);
    List<StockEntry> getStockQuotes();
    public StockEntry getStockQuote(String symbol);
}
