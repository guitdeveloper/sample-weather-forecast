const express = require('express');
const request = require('supertest');
const Server = require('../Server');
const connectDB = require('../config/db');
const { use } = require('chai');

console.log = jest.fn();
jest.mock('../config/db');
jest.mock('../routes/weather');
jest.mock('cors', () => jest.fn(() => (req, res, next) => next()));
const mRouter = { 
  get: jest.fn()
};
jest.spyOn(express, 'Router').mockImplementationOnce(() => mRouter);
const mReq = {};
const mRes = { status: jest.fn().mockReturnThis(), render: jest.fn() };
const mNext = jest.fn();
mRouter.get.mockImplementation((path, callback) => {
  if (path === '') {
    callback(mReq, mRes, mNext);
  }
});

describe('Server', () => {
  let mockApp;
  let server;

  beforeEach(() => {
    mockApp = express()
    jest.spyOn(mockApp, 'listen').mockImplementation(jest.fn());
    server = new Server(mockApp)    
  });

  afterEach(() => {
    jest.resetModules();
    jest.restoreAllMocks();
  });

  it('should Deve inicializar o servidor na porta correta', () => {
    expect(server.port).toBe(process.env.PORT || 5002);
  });

  it('Deve chamar connectDB no início', () => {
    expect(connectDB).toHaveBeenCalled();
  });
});