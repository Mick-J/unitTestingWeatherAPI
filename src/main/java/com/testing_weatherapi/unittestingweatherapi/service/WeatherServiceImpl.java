package com.testing_weatherapi.unittestingweatherapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.testing_weatherapi.unittestingweatherapi.config.WeatherApiProperties;
import com.testing_weatherapi.unittestingweatherapi.model.*;
import com.testing_weatherapi.unittestingweatherapi.web.WeatherController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LogManager.getLogger(WeatherController.class);

    private final WebClient webClient;

    private final WeatherApiProperties weatherApiProperties;

    public WeatherServiceImpl(WebClient.Builder webClientBuilder, WeatherApiProperties weatherApiProperties) {
        this.weatherApiProperties = weatherApiProperties;
        this.webClient = webClientBuilder
                .baseUrl(weatherApiProperties.getUrl())
                .build();
    }

    @Override
    public Mono<ResponseEntity<Weather>> getWeather(String city) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", weatherApiProperties.getKey()) // API Key injected from properties
                        .queryParam("q", city)
                        .queryParam("aqi", "yes")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {

                    String city_w = json.path("location").path("name").asText();
                    String region = json.path("location").path("region").asText();
                    String country = json.path("location").path("country").asText();
                    String localtime = json.path("location").path("localtime").asText();
                    double temperature = json.path("current").path("temp_c").asDouble();
                    String condition = json.path("current").path("condition").path("text").asText();
                    double windSpeed = json.path("current").path("wind_kph").asDouble();

                    Location location = new Location(city_w, region, country, localtime);
                    Weather weather = new Weather(location, temperature, condition, windSpeed);
                    return ResponseEntity.ok(weather);
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    logger.info("Error fetching weather: " + ex.getMessage());
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                    }
                    return Mono.error(ex); // Other errors should still propagate
                });
    }

    @Override
    public Mono<ResponseEntity<Alerts>> getAlerts(String city) {
        ObjectMapper objectMapper = new ObjectMapper();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/alerts.json")
                        .queryParam("key", weatherApiProperties.getKey()) // API Key injected from properties
                        .queryParam("q", city)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    String city_w = json.path("location").path("name").asText();
                    String region = json.path("location").path("region").asText();
                    String country = json.path("location").path("country").asText();
                    String localtime = json.path("location").path("localtime").asText();

                    List<Alert> alertList = new ArrayList<>();

                    // Navigate to "alerts.alert" array in the JSON
                    JsonNode alertsNode = json.path("alerts").path("alert");
                    if (alertsNode.isArray()) {
                        for (JsonNode alertNode : alertsNode) {
                            try {
                                Alert alert = objectMapper.treeToValue(alertNode, Alert.class);
                                alertList.add(alert);
                            } catch (Exception e) {
                                logger.info("Error when creating alert object: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                    Location location = new Location(city_w, region, country, localtime);
                    Alerts alerts =  new Alerts(location, alertList);
                    return ResponseEntity.ok(alerts);
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    logger.info("Error fetching Alerts: " + ex.getMessage());
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                    }
                    return Mono.error(ex); // Other errors should still propagate
                });
    }

    @Override
    public Mono<ResponseEntity<Astronomy>> getAstronomy(String city) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/astronomy.json")
                        .queryParam("key", weatherApiProperties.getKey()) // API Key injected from properties
                        .queryParam("q", city)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map( json -> {

                    String city_w = json.path("location").path("name").asText();
                    String region = json.path("location").path("region").asText();
                    String country = json.path("location").path("country").asText();
                    String localtime = json.path("location").path("localtime").asText();

                    String sunrise = json.path("astronomy").path("astro").path("sunrise").asText();
                    String sunset = json.path("astronomy").path("astro").path("sunset").asText();
                    String moonrise = json.path("astronomy").path("astro").path("moonrise").asText();
                    String moonset = json.path("astronomy").path("astro").path("moonset").asText();
                    String moon_phase = json.path("astronomy").path("astro").path("moon_phase").asText();
                    boolean is_moon_up = json.path("astronomy").path("astro").path("is_moon_up").asBoolean();
                    boolean is_sun_up = json.path("astronomy").path("astro").path("is_sun_up").asBoolean();

                    Location location = new Location(city_w, region, country, localtime);
                    Astronomy astronomy = new Astronomy(location, sunrise, sunset, moonrise, moonset, moon_phase, is_moon_up, is_sun_up);
                    return ResponseEntity.ok(astronomy);
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    logger.info("Error fetching astronomy: " + ex.getMessage());
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                    }
                    return Mono.error(ex); // Other errors should still propagate
                });
    }
}