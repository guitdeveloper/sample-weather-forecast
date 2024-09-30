const express = require('express');
const cors = require('cors');
const connectDB = require('./config/db');
const dotenv = require('dotenv');
const weatherRoutes = require('./routes/weather');

class Server {
  constructor(app = express()) {
    dotenv.config();
    this.app = app;
    this.port = process.env.PORT || 5002;

    this.middlewares();
    this.routes();
    this.start();
  }

  middlewares() {
    this.app.use(cors());
    this.app.use(express.json());
  }

  routes() {
    this.app.use('/api/weather', weatherRoutes);
  }

  start() {
    connectDB();
    this.app.listen(this.port, () => {
      console.log(`Server is running on port ${this.port}`);
    });
  }
}

module.exports = Server;