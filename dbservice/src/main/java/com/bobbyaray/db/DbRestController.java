package com.bobbyaray.db;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private InfluxDB influxDB;

    @Autowired
    @Qualifier("dbname")
    private String dbName;

    @RequestMapping(value = "/saveprice", method = RequestMethod.POST)
    public ResponseEntity<String> saveprice(@RequestBody StockEntry stockEntry) {
        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        Point point = Point.measurement("stock_data")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("price", stockEntry.getCurrentPrice())
                .tag("Symbol", stockEntry.getSymbol()) // tag the individual point
                .build();

        batchPoints.point(point);
        influxDB.write(batchPoints);

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/stock/{symbol}", method = RequestMethod.GET)
    public ResponseEntity<List<StockPoint>> getStock(@PathVariable("symbol") String symbol) {
        Query query = new Query("SELECT * FROM stock_data", dbName);
        QueryResult queryResult = influxDB.query(query);

        List<StockPoint> stockPoints = new ArrayList<StockPoint>();
        for(QueryResult.Result result: queryResult.getResults()){
            for (QueryResult.Series series : result.getSeries()) {
                List<List<Object>> values = series.getValues();
                for(List<Object> value: values){
                    StockPoint point = new StockPoint();
                    point.setTime((String)value.get(0));
                    point.setValue((Double)value.get(3));
                    stockPoints.add(point);
                }
            }
        }

        return new ResponseEntity<List<StockPoint>>(stockPoints, HttpStatus.OK);
    }
}
