package com.sample.weatherforecast.controller;

import com.sample.weatherforecast.dto.WeatherDTO;
import com.sample.weatherforecast.fixture.WeatherFixture;
import com.sample.weatherforecast.model.Weather;
import com.sample.weatherforecast.service.WeatherRegisterApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WeatherRegisterApiControllerTest {

    @Mock
    private WeatherRegisterApiService weatherService;

    @InjectMocks
    private WeatherRegisterApiController weatherController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllWeathers_WhenCityIsNotProvided() {
        List<Weather> weatherList = Collections.singletonList(WeatherFixture.WEATHER);
        when(weatherService.getAllWeathers(null)).thenReturn(weatherList);

        ResponseEntity<List<WeatherDTO>> response = weatherController.getAllWeathers(null);
        WeatherDTO item = Objects.requireNonNull(response.getBody()).get(0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(WeatherFixture.ID, item.getId());
        assertEquals(WeatherFixture.CITY, item.getCity());
        assertEquals(WeatherFixture.COUNTRY, item.getCountry());
        assertEquals(WeatherFixture.DESCRIPTION, item.getDescription());
        assertEquals(WeatherFixture.TEMPERATURE, item.getTemperature());
        assertEquals(WeatherFixture.HUMIDITY, item.getHumidity());
        assertEquals(WeatherFixture.WIND_SPEED, item.getWindSpeed());
        assertEquals(WeatherFixture.CARBON_MONOXIDE, item.getCarbonMonoxide());
        assertNotNull(item.getDateTime());
    }

    @Test
    public void getAllWeathers_WhenCityIsProvided() {
        List<Weather> weatherList = List.of(WeatherFixture.WEATHER);
        when(weatherService.getAllWeathers(WeatherFixture.CITY)).thenReturn(weatherList);

        ResponseEntity<List<WeatherDTO>> response = weatherController.getAllWeathers(WeatherFixture.CITY);
        WeatherDTO item = Objects.requireNonNull(response.getBody()).get(0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(WeatherFixture.CITY, item.getCity());
        assertEquals(WeatherFixture.COUNTRY, item.getCountry());
    }

    @Test
    public void getAllWeathers_WhenNotFound() {
        when(weatherService.getAllWeathers(null)).thenReturn(Collections.emptyList());

        ResponseEntity<List<WeatherDTO>> response = weatherController.getAllWeathers(null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testSaveWeather_Success() {
        when(weatherService.save(any(Weather.class))).thenReturn(WeatherFixture.WEATHER);

        ResponseEntity<WeatherDTO> response = weatherController.save(WeatherFixture.WEATHER_DTO);
        WeatherDTO item = Objects.requireNonNull(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(WeatherFixture.ID, item.getId());
        assertEquals(WeatherFixture.CITY, item.getCity());
        assertEquals(WeatherFixture.COUNTRY, item.getCountry());
        assertEquals(WeatherFixture.DESCRIPTION, item.getDescription());
        assertEquals(WeatherFixture.TEMPERATURE, item.getTemperature());
        assertEquals(WeatherFixture.HUMIDITY, item.getHumidity());
        assertEquals(WeatherFixture.WIND_SPEED, item.getWindSpeed());
        assertEquals(WeatherFixture.CARBON_MONOXIDE, item.getCarbonMonoxide());
        assertNotNull(item.getDateTime());
    }
}