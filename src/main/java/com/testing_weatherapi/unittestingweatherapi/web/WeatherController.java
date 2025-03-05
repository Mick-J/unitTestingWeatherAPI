package com.testing_weatherapi.unittestingweatherapi.web;

import com.testing_weatherapi.unittestingweatherapi.model.*;
import com.testing_weatherapi.unittestingweatherapi.service.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private static final Logger logger = LogManager.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current.json")
    public Mono<ResponseEntity<Weather>> getWeather(@RequestParam String city ) {
        logger.info("Weather API path: /current.jon is called");
        return weatherService.getWeather(city)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/alerts.json")
    public Mono<ResponseEntity<Alerts>> getAlertListWeather(@RequestParam String city ) {
        logger.info("Weather API path: /alerts.jon is called");
        return weatherService.getAlerts(city)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/astronomy.json")
    public Mono<ResponseEntity<Astronomy>> getAstronomy(@RequestParam String city) {
        logger.info("Weather API path: /astronomy.jon is called");
        return weatherService.getAstronomy(city)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}