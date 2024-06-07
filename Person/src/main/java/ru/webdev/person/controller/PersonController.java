package ru.webdev.person.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.webdev.person.model.Person;
import ru.webdev.person.repository.PersonRepository;
import ru.webdev.weather.model.Weather;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping
    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }


    @GetMapping("{id}/weather")
    public ResponseEntity<Weather> getWeather(@PathVariable int id) {
        try {
            if (repository.existsById(id)) {
                String location = repository.findById(id).get().getLocation();
                Weather weather = restTemplate.getForObject("http://localhost:8082/location?name=" + location, Weather.class);

                log.info("Received weather data from Location service: {}", weather);
                return ResponseEntity.ok(weather);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage(), e);
            Weather errorWeather = new Weather();
            errorWeather.setErrorMessage("Error fetching weather data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorWeather);
        }
    }

}
