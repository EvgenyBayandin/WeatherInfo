package ru.webdev.location.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.weather.model.Weather;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @GetMapping
    public ResponseEntity<Weather> getWeather(@RequestParam String name) {
        try {
            Weather weather = restTemplate.getForObject("http://localhost:8083/weather/" + name, Weather.class);
            log.info("Received weather data from Weather service: {}", weather);
            if (weather != null && weather.getErrorMessage() == null) {
                return ResponseEntity.ok(weather);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage(), e);
            Weather errorWeather = new Weather();
            errorWeather.setErrorMessage("Error fetching weather data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorWeather);
        }
    }

}
