package com.testing_weatherapi.unittestingweatherapi.service;

import com.testing_weatherapi.unittestingweatherapi.model.*;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface WeatherService {

    Mono<ResponseEntity<Weather>> getWeather(String city);

    Mono<ResponseEntity<Alerts>> getAlerts(String city);

    Mono<ResponseEntity<Astronomy>> getAstronomy(String city);

}
