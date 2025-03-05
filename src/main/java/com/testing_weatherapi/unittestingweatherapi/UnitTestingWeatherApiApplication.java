package com.testing_weatherapi.unittestingweatherapi;

import com.testing_weatherapi.unittestingweatherapi.config.WeatherApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(WeatherApiProperties.class)
public class UnitTestingWeatherApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitTestingWeatherApiApplication.class, args);
    }

}
