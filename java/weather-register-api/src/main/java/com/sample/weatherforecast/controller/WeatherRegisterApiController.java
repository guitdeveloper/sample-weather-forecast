package com.sample.weatherforecast.controller;

import com.sample.weatherforecast.dto.WeatherDTO;
import com.sample.weatherforecast.model.Weather;
import com.sample.weatherforecast.service.WeatherRegisterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/weathers")
public class WeatherRegisterApiController {

    @Autowired
    private WeatherRegisterApiService weatherService;

    @GetMapping
    public ResponseEntity<List<WeatherDTO>> getAllWeathers(@RequestParam(required = false) String city) {
        List<Weather> weathers = weatherService.getAllWeathers(city);
        try {
            if (weathers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<WeatherDTO> weatherDTOs = weathers.stream().map(WeatherDTO::new).collect(Collectors.toList());
            return ResponseEntity.ok(weatherDTOs);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<WeatherDTO> save(@RequestBody WeatherDTO weather) {
        Weather savedWeather = weatherService.save(new Weather(weather));
        return ResponseEntity.status(HttpStatus.CREATED).body(new WeatherDTO(savedWeather));
    }
}