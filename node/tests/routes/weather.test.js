const request = require('supertest');
const express = require('express');
const mongoose = require('mongoose');
const Weather = require('../../models/Weather');
const weatherRouter = require('../../routes/weather');

const app = express();
app.use(express.json());
app.use('/weather', weatherRouter);

jest.mock('../../models/Weather');

describe('Weather', () => {
  beforeAll(async () => {
    await mongoose.connect('mongodb://localhost/test');
  });

  afterAll(async () => {
    await mongoose.connection.close();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('GET /api/weather', () => {
    it('should return all weather data', async () => {
      const mockWeatherData = [
        { city: 'São Paulo', country: 'BR', temperature: 30 },
        { city: 'Rio de Janeiro', country: 'BR', temperature: 28 },
      ];
      Weather.find.mockResolvedValue(mockWeatherData);

      const response = await request(app).get('/weather');
      expect(response.statusCode).toBe(200);
      expect(response.body).toEqual(mockWeatherData);
      expect(Weather.find).toHaveBeenCalledTimes(1);
    });

    it('"should return a 500 error if there is a problem retrieving the data', async () => {
      Weather.find.mockRejectedValue(new Error('Error database'));

      const response = await request(app).get('/weather');
      expect(response.statusCode).toBe(500);
      expect(response.body).toEqual({ message: 'Error database' });
    });
  });

  describe('POST /api/weather', () => {
    it('should create a new weather record', async () => {
      const newWeatherData = {
        city: 'Belo Horizonte',
        country: 'BR',
        description: 'clean sky',
        temperature: 25,
        humidity: 40,
        windSpeed: 10,
        carbonMonoxide: 0,
      };

      Weather.prototype.save = jest.fn().mockResolvedValue(newWeatherData);

      const response = await request(app).post('/weather').send(newWeatherData);
      expect(response.statusCode).toBe(201);
      expect(response.body).toEqual(newWeatherData);
      expect(Weather.prototype.save).toHaveBeenCalled();
    });

    it('should return a 500 error if there is a problem saving the data.', async () => {
      const newWeatherData = {
        city: 'Curitiba',
        country: 'BR',
        description: 'clean sky',
        windSpeed: 5,
        carbonMonoxide: 0,
      };

      Weather.prototype.save = jest.fn().mockRejectedValue(new Error('Error to save'));

      const response = await request(app).post('/weather').send(newWeatherData);
      expect(response.statusCode).toBe(500);
      expect(response.body).toEqual({ message: 'Error to save' });
    });
  });
});