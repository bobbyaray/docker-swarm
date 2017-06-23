package com.bobbyaray.db;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by robertray on 6/7/17.
 */
@Configuration
public class DbConfig {
    @Value("${spring.influxdb.url}")
    private String dbUrl;

    @Value("${spring.influxdb.username}")
    private String dbUser;

    @Value("${spring.influxdb.password}")
    private String dbPass;

    @Value("${spring.influxdb.database}")
    private String dbName;

    @Bean("dbname")
    public String getDbName(){
        return dbName;
    }

    @Bean
    public InfluxDB getInfluxDB(){
        InfluxDB influxDB = InfluxDBFactory.connect(dbUrl, dbUser, dbPass);
        influxDB.createDatabase(dbName);
        return influxDB;
    }
}
