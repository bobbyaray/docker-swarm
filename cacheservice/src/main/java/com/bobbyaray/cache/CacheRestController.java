package com.bobbyaray.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by robertray on 6/22/17.
 */
@RestController
public class CacheRestController {
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/stocks", method = RequestMethod.GET)
    public ResponseEntity<List<StockEntry>> getStocks() {
        return new ResponseEntity<>(stockService.getStockQuotes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<StockEntry> getStock(@PathVariable("symbol") String symbol) {
        return new ResponseEntity<>(stockService.getStockQuote(symbol), HttpStatus.OK);
    }
}
