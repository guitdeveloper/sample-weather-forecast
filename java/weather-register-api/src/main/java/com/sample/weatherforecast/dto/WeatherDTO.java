package com.sample.weatherforecast.dto;

import com.sample.weatherforecast.model.Weather;
import java.time.LocalDateTime;

public class WeatherDTO {

    private String id;
    private String city;
    private String country;
    private String description;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private Double carbonMonoxide;
    private LocalDateTime dateTime;

    public WeatherDTO() {}

    public WeatherDTO(Weather weather) {
        this.id = weather.getId();
        this.city = weather.getCity();
        this.country = weather.getCountry();
        this.description = weather.getDescription();
        this.temperature = weather.getTemperature();
        this.humidity = weather.getHumidity();
        this.windSpeed = weather.getWindSpeed();
        this.carbonMonoxide = weather.getCarbonMonoxide();
        this.dateTime = weather.getDateTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getCarbonMonoxide() {
        return carbonMonoxide;
    }

    public void setCarbonMonoxide(Double carbonMonoxide) {
        this.carbonMonoxide = carbonMonoxide;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
