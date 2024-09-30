export interface WeatherData {
  city: string;
  country: string;
  carbonMonoxide: number;
  dateTime: string;
  description: string;
  temperature: number;
}

export interface CityPollution {
  city: string;
  country: string;
  carbonMonoxide: number;
}