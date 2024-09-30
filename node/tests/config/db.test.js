const mongoose = require('mongoose');
const connectDB = require('../../config/db');

jest.mock('mongoose');
console.log = jest.fn();
console.error = jest.fn();
process.exit = jest.fn();

describe('connectDB', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should connect to MongoDB successfully', async () => {
    mongoose.connect.mockResolvedValueOnce();

    await connectDB();

    expect(mongoose.connect).toHaveBeenCalledWith(process.env.MONGO_URI);
    expect(console.log).toHaveBeenCalledWith('MongoDB connected!');
  });

  it('should handle errors when connecting to MongoDB', async () => {
    const errorMessage = 'Connection error';
    mongoose.connect.mockRejectedValueOnce(new Error(errorMessage));

    await connectDB();

    expect(mongoose.connect).toHaveBeenCalledWith(process.env.MONGO_URI);
    expect(console.error).toHaveBeenCalledWith(`Error to connect MongoDB: ${errorMessage}`);
    expect(process.exit).toHaveBeenCalledWith(1);
  });
});