const express = require('express');
const Weather = require('../models/Weather');

const router = express.Router();

router.get('/', async (req, res) => {
  try {
    const weatherData = await Weather.find();
    res.json(weatherData);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

router.post('/', async (req, res) => {
  const weather = new Weather({
    city: req.body.city,
    country: req.body.country,
    description: req.body.description,
    temperature: req.body.temperature,
    humidity: req.body.humidity,
    windSpeed: req.body.windSpeed,
    carbonMonoxide: req.body.carbonMonoxide,
  });

  try {
    const newWeather = await weather.save();
    res.status(201).json(newWeather);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;