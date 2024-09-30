const mongoose = require('mongoose');

const WeatherSchema = new mongoose.Schema({
  city: {
    type: String,
    required: true
  },
  country: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  },
  temperature: {
    type: Number,
    required: true
  },
	humidity: {
    type: Number,
    required: true
  },
	windSpeed: {
    type: Number,
    required: true
  },
  carbonMonoxide: {
    type: Number,
    required: true
  },
  dateTime: { type: Date, default: Date.now },
});

module.exports = mongoose.model('Weather', WeatherSchema);