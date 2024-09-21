import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import axios from 'axios';

const Weather = () => {
  const [city, setCity] = useState('');
  const [weather, setWeather] = useState(null);
  const [airPollution, setAirPollution] = useState(null);
  const [error, setError] = useState(null);

  const apiKey = 'ADD HERE YOUR openweathermap API KEY';
  const weatherRegisterApiDomainAndPort = 'IP:PORT'

  const openweathermapApi = 'https://api.openweathermap.org/data/2.5'

  const getWeather = async () => {
    try {
      setError(null);
      const response = await axios.get(
        `${openweathermapApi}/weather?q=${city}&appid=${apiKey}&units=metric`
      );
      if (response.status === 200 || response.status === 201) {
        setCity('')
        setWeather(response.data);
        getAirPollution(response.data)
      } else {
        setError('No information found for the city.');
      }
    } catch (err) {
      setError('City not found.');
      setWeather(null);
      setAirPollution(null);
    }
  };

  const getAirPollution = async (weatherObj) => {
    try {
      const response = await axios.get(
        `${openweathermapApi}/air_pollution?lat=${weatherObj.coord.lat}&lon=${weatherObj.coord.lon}&appid=${apiKey}`
      );
      if (response.status === 200 || response.status === 201) {
        setAirPollution(response.data.list[0].components);
        registerWeather(weatherObj, response.data.list[0].components);
      } else {
        setError('Data about air pollution not found.');
      }
    } catch (err) {
      setAirPollution(null);
    }
  };

  const registerWeather = async (weatherObj, airPollutionObj) => {
    try {
      const weatherData = {
        city: weatherObj.name,
        country: weatherObj.sys.country,
        description: weatherObj.weather[0].description,
        temperature: weatherObj.main.temp,
        humidity: weatherObj.main.humidity,
        windSpeed: weatherObj.wind.speed,
        carbonMonoxide: airPollutionObj.co,
      };
      const response = await axios.post(
        `http://${weatherRegisterApiDomainAndPort}/api/weathers`, weatherData
      );
      if (response.status === 200 || response.status === 201) {
        Alert.alert('Success', 'Weather data submitted successfully!');
      } else {
        Alert.alert('Error', `Failed to submit data. Status: ${response.status}`);
      }
    } catch (err) {
      setError('Not register weather.' + err);
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Weather Forecast</Text>
      <TextInput
        style={styles.input}
        placeholder="Enter the name of the city"
        value={city}
        onChangeText={text => setCity(text.trim())}
      />
      <Button title="Search Forecast" onPress={getWeather} />
      {weather && (
        <View>
          <Text>Localization: {weather.name}, {weather.sys.country}</Text>
          <Text>Description: {weather.weather[0].description}</Text>
          <Text>Temperature: {weather.main.temp} °C</Text>
          <Text>Humidity: {weather.main.humidity}%</Text>
          <Text>Wind speed: {weather.wind.speed} m/s</Text>
        </View>
      )}
      {airPollution && (
        <View>
          <Text>Carbon monoxide: {airPollution.co} μg/m3</Text>
        </View>
      )}
      {error && <Text style={styles.error}>{error}</Text>}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    paddingTop: 100,
    paddingBottom: 20,
    paddingLeft: 20,
    paddingRight: 20,
  },
  title: {
    fontSize: 30,
    marginBottom: 20,
    color: 'orange',
    textAlign: 'center',
  },
  label: {
    fontSize: 16,
    marginBottom: 8,
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 20,
    paddingLeft: 8,
  },
  error: {
    fontSize: 16,
    color: 'red',
  },
});

export default Weather;