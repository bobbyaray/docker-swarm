package com.bobbyaray.stock;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by robertray on 6/23/17.
 */
@Component
public class StockTasks {
    @Value("${spring.dbservice.host}")
    private String dbHost;

    @Value("${spring.dbservice.port}")
    private String dbPort;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int rangeMin = 80;
    private int rangeMax = 100;
    private final String PRICE_URL = "/saveprice";

    private String[] symbols = {"MSFT", "AAPL", "GOOGL", "TWTR", "FB"};

    @Scheduled(fixedDelay = 20000)
    public void runStockTicker(){
        System.out.println("Running stock ticker");
        for(String symbol: symbols){
            double newPrice = this.generatePrice();
            this.sendStockUpdateToDB(new StockEntry(symbol, newPrice));
            this.publishPriceToQueue(new StockEntry(symbol, newPrice));
        }
    }

    private void sendStockUpdateToDB(StockEntry newEntry){
        System.out.println("Sending New Price: " + newEntry.toString());
        RestTemplate rest = new RestTemplate();

        String result = rest.postForObject(this.getUrl(), newEntry, String.class);
        System.out.println("Result: " + result);
    }

    private void publishPriceToQueue(StockEntry newEntry){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        this.rabbitTemplate.convertAndSend("stockqueue", newEntry);
    }

    private double generatePrice(){
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    private String getUrl(){
        return "http://" + dbHost + ":" + dbPort + PRICE_URL;
    }
}
