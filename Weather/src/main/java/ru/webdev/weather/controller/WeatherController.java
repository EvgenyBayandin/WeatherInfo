package ru.webdev.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.webdev.weather.model.Weather;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    @Value("${url.weather}")
    private String urlToRequest;

    @Value("${appId}")
    private String appId;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{location}")
    public ResponseEntity<Weather> getWeather(@PathVariable String location) {
        try {

            String url = urlToRequest + "q=" + location + "&appid=" + appId + "&units=metric";
            log.info("Requesting weather data from: {}", url);

            Weather weather = restTemplate.getForObject(url, Weather.class);
            log.info("Received weather data: {}", weather);
            if (weather != null && weather.getCod() == 200) {

                return ResponseEntity.ok(weather);
            } else {
                Weather errorWeather = new Weather();
                errorWeather.setErrorMessage("Weather data not found for " + location);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorWeather);
            }
        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage(), e);
            Weather errorWeather = new Weather();
            errorWeather.setErrorMessage("Error fetching weather data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorWeather);
        }
    }
}
