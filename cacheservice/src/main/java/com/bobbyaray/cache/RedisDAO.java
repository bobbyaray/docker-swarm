package com.bobbyaray.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by robertray on 6/19/17.
 */
@Component
public class RedisDAO {
    @Autowired
    private Jedis jedis;

    public void insertStockQuote(String symbol, String price){
        jedis.set(symbol, price);
    }

    public List<StockEntry> getStockQuotes(){
        List<StockEntry> stockEntries = new ArrayList<StockEntry>();
        Set<String> symbols = jedis.keys("*");
        for(String symbol: symbols){
            stockEntries.add(new StockEntry(symbol, Double.parseDouble(jedis.get(symbol))));
        }
        return stockEntries;
    }

    public String getStockQuote(String symbol){
        return jedis.get(symbol);
    }
}
