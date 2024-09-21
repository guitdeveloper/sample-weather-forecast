package com.sample.controller;

import com.sample.model.Weather;
import com.sample.repository.WeatherRegisterApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class WeatherRegisterApiController {

    @Autowired
    WeatherRegisterApiRepository weatherRepository;

    @GetMapping("/weathers")
    public ResponseEntity<List<Weather>> getWeatherAll(@RequestParam(required = false) String city) {
        try {
            List<Weather> list = new ArrayList<Weather>();
            if (city == null)
                list.addAll(weatherRepository.findAll());
            else
                list.addAll(weatherRepository.findByCity(city));
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/weathers/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable("id") String id) {
        Optional<Weather> obj = weatherRepository.findById(id);
        return obj
                .map(weather -> new ResponseEntity<>(weather, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/weathers")
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {
        weather.setDateTime(LocalDateTime.now());
        Weather savedWeather = weatherRepository.save(weather);
        return new ResponseEntity<>(savedWeather, HttpStatus.CREATED);
    }
}