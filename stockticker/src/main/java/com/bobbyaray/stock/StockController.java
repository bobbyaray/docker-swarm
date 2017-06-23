package com.bobbyaray.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by robertray on 6/5/17.
 */
@Controller
public class StockController {
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "welcome";
    }
}
