package com.sample.repository;

import com.sample.model.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WeatherRegisterApiRepository extends MongoRepository<Weather, String> {
    List<Weather> findByCity(String city);
}
