package com.bobbyaray.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by robertray on 6/19/17.
 */
@Component
public class MqService {
    @Autowired
    private RedisDAO redisDAO;

    @RabbitListener(queues = "stockqueue")
    public void processMessage(String content){
        System.out.println("Received: " + content);
        ObjectMapper mapper = new ObjectMapper();
        try {
            StockEntry entry = mapper.readValue(content, StockEntry.class);
            System.out.println("Entry Price: " + entry.getCurrentPrice());
            redisDAO.insertStockQuote(entry.getSymbol(), entry.getCurrentPrice()+"");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
