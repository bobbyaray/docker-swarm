package com.bobbyaray.db;

import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by robertray on 6/7/17.
 */
@RestController
public class DbRestController {
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    @RequestMapping(value = "/saveprice", method = RequestMethod.POST)
    public ResponseEntity<String> saveprice(@RequestBody StockEntry stockEntry) {
        System.out.println("Received: " + stockEntry.toString());
        influxDBTemplate.createDatabase();
        final Point p = Point.measurement("stock_data")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("symbol", stockEntry.getSymbol())
                .addField("value", stockEntry.getCurrentPrice())
                .build();
        influxDBTemplate.write(p);

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<List<StockPoint>> getStock(@PathVariable("symbol") String symbol) {
        Query query = new Query("SELECT * FROM stock_data where symbol = '" + symbol + "'",
                influxDBTemplate.getDatabase());
        QueryResult queryResult = influxDBTemplate.getConnection().query(query);

        List<StockPoint> stockPoints = new ArrayList<StockPoint>();
        for(QueryResult.Result result: queryResult.getResults()){
            for (QueryResult.Series series : result.getSeries()) {
                List<List<Object>> values = series.getValues();
                for(List<Object> value: values){
                    StockPoint point = new StockPoint();
                    point.setTime((String)value.get(0));
                    point.setValue((Double)value.get(2));
                    stockPoints.add(point);
                }
            }
        }

        return new ResponseEntity<List<StockPoint>>(stockPoints, HttpStatus.OK);
    }

    @RequestMapping(value = "/stocks", method = RequestMethod.GET)
    public ResponseEntity<List<StockEntry>> getStocks() {
        List<String> symbols = this.getStockSymbolsFromDB();
        List<StockEntry> stocks = new ArrayList<>();

        for(String symbol: symbols){
            Query query = new Query("SELECT last(*) FROM stock_data where symbol = '" + symbol + "'",
                    influxDBTemplate.getDatabase());
            QueryResult queryResult = influxDBTemplate.getConnection().query(query);

            for(QueryResult.Result result: queryResult.getResults()){
                for (QueryResult.Series series : result.getSeries()) {
                    List<List<Object>> values = series.getValues();
                    for(List<Object> value: values){
                        StockEntry entry = new StockEntry();
                        entry.setCurrentPrice((Double)value.get(2));
                        entry.setSymbol(symbol);
                        stocks.add(entry);
                    }
                }
            }
        }

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    private List<String> getStockSymbolsFromDB(){
        Query query = new Query("select distinct(symbol) from stock_data",
                influxDBTemplate.getDatabase());
        QueryResult queryResult = influxDBTemplate.getConnection().query(query);
        List<String> symbols = new ArrayList<>();
        for(QueryResult.Result result: queryResult.getResults()){
            for (QueryResult.Series series : result.getSeries()) {
                List<List<Object>> values = series.getValues();
                for(List<Object> value: values){
                    symbols.add((String)value.get(1));
                }
            }
        }

        return symbols;
    }
}
