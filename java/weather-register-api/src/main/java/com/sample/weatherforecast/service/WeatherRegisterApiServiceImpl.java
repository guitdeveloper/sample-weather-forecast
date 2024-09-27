package com.sample.weatherforecast.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.sample.weatherforecast.model.Weather;
import com.sample.weatherforecast.repository.WeatherRegisterApiRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherRegisterApiServiceImpl implements WeatherRegisterApiService {
    
    @Autowired
    WeatherRegisterApiRepository weatherRepository;

    @Override
    public Weather save(Weather weather) {
        weather.setDateTime(LocalDateTime.now());
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getAllWeathers(String city) {
        List<Weather> weathers = new ArrayList<>();
        if (city == null)
            weathers.addAll(weatherRepository.findAll());
        else
            weathers.addAll(weatherRepository.findByCity(city));
        return weathers;

    }
}
