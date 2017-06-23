package com.bobbyaray.test;

import com.bobbyaray.db.StockEntry;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Created by robertray on 6/8/17.
 */
public class HttpTests {
    @Test
    public void sendNewStockPrice(){
        RestTemplate rest = new RestTemplate();
        StockEntry entry = new StockEntry("VMW", 92.8);

        String result = rest.postForObject("http://localhost:8080/saveprice", entry, String.class);
        System.out.println("Result: " + result);
    }
}
