package com.sample.weatherforecast.fixture;

import com.sample.weatherforecast.dto.WeatherDTO;
import com.sample.weatherforecast.model.Weather;

import java.time.LocalDateTime;

public class WeatherFixture {

    public static final String ID = "1";
    public static final String CITY = "Niterói";
    public static final String CITY_TWO = "New York";
    public static String COUNTRY = "BR";
    public static String COUNTRY_TWO = "US";
    public static String DESCRIPTION = "overcast cloud";
    public static double TEMPERATURE = 32.6;
    public static int HUMIDITY = 22;
    public static double WIND_SPEED = 18.0;
    public static Double CARBON_MONOXIDE = 340.43;

    public static final Weather WEATHER = createWeather();
    public static final WeatherDTO WEATHER_DTO = createWeatherDTO();

    private static Weather createWeather() {
        Weather weather = new Weather();
        weather.setIdTest(ID);
        weather.setCity(CITY);
        weather.setCountry(COUNTRY);
        weather.setDescription(DESCRIPTION);
        weather.setTemperature(TEMPERATURE);
        weather.setHumidity(HUMIDITY);
        weather.setWindSpeed(WIND_SPEED);
        weather.setCarbonMonoxide(CARBON_MONOXIDE);
        weather.setDateTime(LocalDateTime.now());
        return weather;
    }

    private static WeatherDTO createWeatherDTO() {
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setCity(ID);
        weatherDTO.setCity(CITY);
        weatherDTO.setCountry(COUNTRY);
        weatherDTO.setDescription(DESCRIPTION);
        weatherDTO.setTemperature(TEMPERATURE);
        weatherDTO.setHumidity(HUMIDITY);
        weatherDTO.setWindSpeed(WIND_SPEED);
        weatherDTO.setCarbonMonoxide(CARBON_MONOXIDE);
        return weatherDTO;
    }
}