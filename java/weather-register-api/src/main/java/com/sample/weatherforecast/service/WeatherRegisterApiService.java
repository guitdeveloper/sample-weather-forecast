package com.sample.weatherforecast.service;

import com.sample.weatherforecast.model.Weather;
import java.util.List;

public interface WeatherRegisterApiService {
    Weather save(Weather weather);
    List<Weather> getAllWeathers(String city);
}
