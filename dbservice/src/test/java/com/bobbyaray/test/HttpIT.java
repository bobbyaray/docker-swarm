package com.bobbyaray.test;

import com.bobbyaray.db.StockEntry;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Created by robertray on 6/8/17.
 */
public class HttpIT {
    @Test
    public void sendNewStockPrice(){
        RestTemplate rest = new RestTemplate();
        StockEntry entry = new StockEntry("VMW", 92.8);

        String result = rest.postForObject("http://localhost:8081/saveprice", entry, String.class);
        System.out.println("Result: " + result);
    }
}
