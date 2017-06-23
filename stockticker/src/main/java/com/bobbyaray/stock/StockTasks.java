package com.bobbyaray.stock;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by robertray on 6/23/17.
 */
@Component
public class StockTasks {
    private int rangeMin = 80;
    private int rangeMax = 100;

    private String[] symbols = {"MSFT", "AAPL", "GOOGL", "TWTR", "FB"};

    @Scheduled(fixedDelay = 20000)
    public void runStockTicker(){
        System.out.println("Running stock ticker");
        for(String symbol: symbols){
            double newPrice = this.generatePrice();
            this.sendStockUpdate(symbol, newPrice);
        }
    }

    private void sendStockUpdate(String symbol, double newPrice){
        System.out.println("Sending New Price: " + symbol + " " + newPrice);
    }

    private double generatePrice(){
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
}
