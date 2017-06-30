package com.bobbyaray.cache;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertray on 6/29/17.
 */
@Service
public class StockServiceImpl implements StockService {
    private StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void insertStockQuote(StockEntry stockEntry) {
        stockRepository.save(stockEntry);
    }

    @Override
    public List<StockEntry> getStockQuotes() {
        Iterable<StockEntry> iterable = stockRepository.findAll();
        List<StockEntry> stockList = new ArrayList<StockEntry>();
        iterable.forEach(stockList::add);
        return stockList;
    }

    @Override
    public StockEntry getStockQuote(String symbol) {
        return stockRepository.findOne(symbol);
    }
}
