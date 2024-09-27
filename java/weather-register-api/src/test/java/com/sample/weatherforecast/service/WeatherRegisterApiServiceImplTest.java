package com.sample.weatherforecast.service;

import com.sample.weatherforecast.fixture.WeatherFixture;
import com.sample.weatherforecast.model.Weather;
import com.sample.weatherforecast.repository.WeatherRegisterApiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WeatherRegisterApiServiceImplTest {

    @Mock
    private WeatherRegisterApiRepository weatherRepository;

    @InjectMocks
    private WeatherRegisterApiServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        when(weatherRepository.save(any(Weather.class))).thenAnswer(invocation -> {
            Weather savedWeather = invocation.getArgument(0);
            savedWeather.setIdTest(WeatherFixture.ID);
            savedWeather.setDateTime(LocalDateTime.now());
            return savedWeather;
        });

        Weather result = weatherService.save(WeatherFixture.WEATHER);

        verify(weatherRepository, times(1)).save(WeatherFixture.WEATHER);
        assertEquals(WeatherFixture.CITY, result.getCity());
        assertEquals(WeatherFixture.TEMPERATURE, result.getTemperature());
        assertEquals(WeatherFixture.ID, result.getId());
    }

    @Test
    void getAllWeathers_NoCity() {
        Weather weatherOne = new Weather();
        weatherOne.setCity(WeatherFixture.CITY);
        weatherOne.setTemperature(WeatherFixture.TEMPERATURE);
        weatherOne.setDateTime(LocalDateTime.now());
        Weather weatherTwo = new Weather();
        weatherTwo.setCity(WeatherFixture.CITY_TWO);
        weatherTwo.setTemperature(WeatherFixture.TEMPERATURE);
        weatherTwo.setDateTime(LocalDateTime.now());

        when(weatherRepository.findAll()).thenReturn(Arrays.asList(weatherOne, weatherTwo));

        List<Weather> result = weatherService.getAllWeathers(null);

        verify(weatherRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(WeatherFixture.CITY, result.get(0).getCity());
        assertEquals(WeatherFixture.CITY_TWO, result.get(1).getCity());
    }

    @Test
    void getAllWeathers_WithCity() {
        Weather weather = new Weather();
        weather.setCity(WeatherFixture.CITY);
        weather.setTemperature(WeatherFixture.TEMPERATURE);
        weather.setDateTime(LocalDateTime.now());

        when(weatherRepository.findByCity(WeatherFixture.CITY)).thenReturn(Collections.singletonList(weather));

        List<Weather> result = weatherService.getAllWeathers(WeatherFixture.CITY);

        verify(weatherRepository, times(1)).findByCity(WeatherFixture.CITY);
        assertEquals(1, result.size());
        assertEquals(WeatherFixture.CITY, result.get(0).getCity());
        assertEquals(WeatherFixture.TEMPERATURE, result.get(0).getTemperature());
    }
}