package com.bobbyaray.stock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by robertray on 6/5/17.
 */
@Controller
public class StockController {
    @Value("${spring.dbservice.host}")
    private String dbHost;

    @Value("${spring.dbservice.port}")
    private String dbPort;

    private final String LIST_URL = "/stocks";
    private final String DETAILED_STOCK_URL = "/stock/{symbol}";

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("stocks", this.getCurrentStockPrices());
        return "welcome";
    }

    @RequestMapping("/stockdetails/{symbol}")
    public String stockdetails(@PathVariable String symbol, Model model) {
        model.addAttribute("stockpoints", this.getStockDetails(symbol));
        model.addAttribute("symbol", symbol);
        return "stockdetails";
    }

    private List<StockEntry> getCurrentStockPrices(){
        RestTemplate rest = new RestTemplate();
        List<StockEntry> result = rest.getForEntity(this.getUrl(), List.class).getBody();
        System.out.println(result.toString());
        return result;
    }

    private List<StockPoint> getStockDetails(String symbol){
        RestTemplate rest = new RestTemplate();
        List<StockPoint> result = rest.getForEntity(this.getDetailsUrl(symbol), List.class).getBody();
        System.out.println(result.toString());
        return result;
    }

    private String getUrl(){
        return "http://" + dbHost + ":" + dbPort + LIST_URL;
    }

    private String getDetailsUrl(String symbol){
        String url = "http://" + dbHost + ":" + dbPort + DETAILED_STOCK_URL;
        url = url.replace("{symbol}", symbol);
        return url;
    }
}
