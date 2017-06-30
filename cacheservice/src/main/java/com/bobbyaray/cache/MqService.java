package com.bobbyaray.cache;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by robertray on 6/19/17.
 */
@Component
public class MqService {
    @Autowired
    private StockService stockService;

    @RabbitListener(queues = "stockqueue")
    public void processMessage(StockEntry entry){
        System.out.println("Received from queue: " + entry.toString());
        stockService.insertStockQuote(entry);
    }
}
