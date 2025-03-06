package com.testing_weatherapi.unittestingweatherapi;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.testing_weatherapi.unittestingweatherapi.config.WeatherApiProperties;
import com.testing_weatherapi.unittestingweatherapi.model.Alerts;
import com.testing_weatherapi.unittestingweatherapi.model.Astronomy;
import com.testing_weatherapi.unittestingweatherapi.model.Weather;
import com.testing_weatherapi.unittestingweatherapi.service.WeatherService;
import com.testing_weatherapi.unittestingweatherapi.service.WeatherServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9999)  // We set the port of wiremock to 9999 instead of using a random port
@TestPropertySource(locations = "classpath:test.properties")
public class WeatherServiceWiremockTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("Test Get Weather Record - Success")
    void testGetWeatherRecordSuccess() {
            // Mock response from Weather data
            stubFor(get(urlEqualTo("/current.json"))
                    .withQueryParam("key", matching(".*")) // Match any key
                    .withQueryParam("q", equalTo("Paris"))
                    .withQueryParam("aqi", equalTo("yes"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withStatus(200)
                            .withBodyFile("get-weather-current.json")));

            // Call API
            ResponseEntity<Weather> response = weatherService.getWeather("Paris").block();
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());

            Weather weather = response.getBody();

            assertNotNull(weather);
            assertEquals("Paris", weather.getLocation().getCity(), "The city should be Paris");
            assertEquals("Ile-de-France", weather.getLocation().getRegion(), "The region should be Ile-de-France");
            assertEquals("France", weather.getLocation().getCountry(), "The country should be France");
            assertEquals("2025-03-04 11:39", weather.getLocation().getLocaltime(), "The localtime should be 2025-03-04 11:39");
            assertEquals(7.2, weather.getTemperature(), "The temperature should be 7.2");
            assertEquals("Sunny", weather.getCondition(), "The condition should be Sunny");
            assertEquals(7.6, weather.getWindSpeed(), "The Wind speed should be 7.6");
    }

    @Test
    @DisplayName("Test Get Weather Record - Not Found")
    void testGetWeatherRecordCityNotFound() {
        // Mock response from Weather API for a non-existent city
        stubFor(get(urlEqualTo("/current.json"))
                .withQueryParam("key", matching(".*")) // Match any key
                .withQueryParam("q", equalTo("UnknownCity"))
                .withQueryParam("aqi", equalTo("yes"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));
        // Call API with a city that doesn't exist
        Mono<ResponseEntity<Weather>> responseMono = weatherService.getWeather("UnknownCity");
        // Verify response
        ResponseEntity<Weather> response = responseMono.block();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "The status code should be 404");
        assertNull(response.getBody());
    }


    @Test
    @DisplayName("Test Get Astronomy Record - Success")
    void testGetAstronomyRecordSuccess() {
        // Mock response from Weather-astronomy data
        stubFor(get(urlEqualTo("/astronomy.json"))
                .withQueryParam("key", matching(".*")) // Match any key
                .withQueryParam("q", equalTo("Brussels"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("get-weather-astronomy.json")));

        // Call API
        ResponseEntity<Astronomy> response = weatherService.getAstronomy("Brussels").block();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Astronomy astronomy = response.getBody();
        System.out.println(astronomy.toString());

        assertNotNull(astronomy);
        assertEquals("Brussels", astronomy.getLocation().getCity(), "City name should be Brussels");
        assertEquals("", astronomy.getLocation().getRegion(), "Region should be empty");
        assertEquals("Belgium", astronomy.getLocation().getCountry(), "Country should be Belgium");
        assertEquals("2025-03-04 21:05", astronomy.getLocation().getLocaltime(), "Localtime  should be 2025-03-04 21:05");
        assertEquals("07:20 AM", astronomy.getSunrise(), "Sunrise time should be 07:20 AM");
        assertEquals("06:29 PM", astronomy.getSunset(), "Sunset time  should be 06:29 PM");
        assertEquals("08:49 AM", astronomy.getMoonrise(), "Moonrise  should be 08:49 AM");
        assertEquals("No moonset", astronomy.getMoonset(), "Moonset value should be No moonset");
        assertEquals("Waxing Crescent", astronomy.getMoon_phase(), "Moon_phase should be Waxing Crescent");
        assertEquals(false, astronomy.isIs_moon_up(), "Is_moon_up should be false");
        assertEquals(false, astronomy.isIs_sun_up() , "Is_sun_up should be false");
    }

    @Test
    @DisplayName("Test Get Astronomy Record - Not Found")
    void testGetAstronomyRecordNotFound() {
        // Mock response from Weather API astronomy for a non-existent city
        stubFor(get(urlEqualTo("/astronomy.json"))
                .withQueryParam("key", matching(".*")) // Match any key
                .withQueryParam("q", equalTo("Unknown"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));
        // Call API with a city that doesn't exist
        Mono<ResponseEntity<Astronomy>> responseMono = weatherService.getAstronomy("Unknown");
        // Verify response
        ResponseEntity<Astronomy> response = responseMono.block();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "The status code should be 404");
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Test Get Alerts Records - Success")
    void testGetAlertsRecordSuccess() {
        // Mock response from Weather alerts data
        stubFor(get(urlEqualTo("/alerts.json"))
                .withQueryParam("key", matching(".*")) // Match any key
                .withQueryParam("q", equalTo("New York"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("get-weather-alerts.json")));

        // Call API
        ResponseEntity<Alerts> response = weatherService.getAlerts("New York").block();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Alerts alerts = response.getBody();
        assertNotNull(alerts);
        assertEquals(5, alerts.getAlerts().size());
    }

    @Test
    @DisplayName("Test Get Single Alert Record - Success")
    void testGetSingleAlertRecordSuccess() {
        stubFor(get(urlEqualTo("/alerts.json"))
                .withQueryParam("key", matching(".*")) // Match any key
                .withQueryParam("q", equalTo("New York"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("get-weather-alerts.json")));

        // Call API
        ResponseEntity<Alerts> response = weatherService.getAlerts("New York").block();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Alerts alerts = response.getBody();
        assertNotNull(alerts);

        // assert single alert return data
        // check location data
        assertEquals("New York", alerts.getLocation().getCity(), "City Shoud be New York");
        assertEquals("New York", alerts.getLocation().getRegion(), "Region Shoud be New York");
        assertEquals("United States of America", alerts.getLocation().getCountry(), "Country Shoud be United States of America");
        assertEquals("2025-03-04 12:04", alerts.getLocation().getLocaltime(), "Localtime Shoud be 2025-03-04 12:04");
        // check second alert data inside alert list returned
        assertEquals("", alerts.getAlerts().get(1).getHeadline(), "Headline Shoud be empty");
        assertEquals("Unknown", alerts.getAlerts().get(1).getSeverity(), "Severity Shoud be Unknown");
        assertEquals("Montgomery", alerts.getAlerts().get(1).getAreas(), "Areas Shoud be Montgomery");
        assertEquals("Met", alerts.getAlerts().get(1).getCategory(), "Category Shoud be Met");
        assertEquals("2025-03-04T12:29:09-00:00", alerts.getAlerts().get(1).getEffective(), "Effective Shoud be 2025-03-04T12:29:09-00:00");
        assertEquals("2025-03-04T12:39:09-00:00", alerts.getAlerts().get(1).getExpires(), "Expires Shoud be 2025-03-04T12:39:09-00:00");
    }

    @Test
    @DisplayName("Test Get Alert Record - Not Found")
    void testGetAlertRecordNotFound() {
        // Mock response from Weather API alerts for a non-existent city
        stubFor(get(urlEqualTo("/alerts.json"))
                .withQueryParam("key", matching(".*")) // Match any key
                .withQueryParam("q", equalTo("Unknown"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));
        // Call API with a city that doesn't exist
        Mono<ResponseEntity<Alerts>> responseMono = weatherService.getAlerts("Unknown");
        // Verify response
        ResponseEntity<Alerts> response = responseMono.block();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "The status code should be 404");
        assertNull(response.getBody());
    }

    // Test Configuration bean
    @TestConfiguration
    static class TestConfig {
        @Bean
        public WeatherApiProperties weatherApiProperties(Environment env) {
            WeatherApiProperties properties = new WeatherApiProperties();
            properties.setKey(env.getProperty("weather.api.key", "test-api-key")); // Get from test.properties or default
            properties.setUrl(env.getProperty("weather.api.url", "http://localhost:9999"));
            return properties;
        }

        @Bean
        public WebClient.Builder webClientBuilder() {
            return WebClient.builder();
        }

        @Bean
        public WeatherService weatherService(WebClient.Builder webClientBuilder, WeatherApiProperties weatherApiProperties) {
            return new WeatherServiceImpl(webClientBuilder, weatherApiProperties);
        }
    }
}
