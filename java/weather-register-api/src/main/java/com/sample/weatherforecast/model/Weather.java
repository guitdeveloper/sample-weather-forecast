package com.sample.weatherforecast.model;

import com.sample.weatherforecast.dto.WeatherDTO;
import com.sample.weatherforecast.utils.VisibleForTesting;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "weathers")
public class Weather {

    @Id
    private String id;
    private String city;
    private String country;
    private String description;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private Double carbonMonoxide;
    private LocalDateTime dateTime;

    public Weather() {}

    public Weather(WeatherDTO weatherDTO) {
        this.city = weatherDTO.getCity();
        this.country = weatherDTO.getCountry();
        this.description = weatherDTO.getDescription();
        this.temperature = weatherDTO.getTemperature();
        this.humidity = weatherDTO.getHumidity();
        this.windSpeed = weatherDTO.getWindSpeed();
        this.carbonMonoxide = weatherDTO.getCarbonMonoxide();
    }

    public String getId() {
        return id;
    }

    @VisibleForTesting
    public void setIdTest(String id) {
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