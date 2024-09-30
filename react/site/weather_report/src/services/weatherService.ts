import { WeatherData, CityPollution } from "../types/weatherTypes";

export const fetchWeatherData = async (): Promise<WeatherData[]> => {
  const response = await fetch("/api/weather");
  const data: WeatherData[] = await response.json();
  return data;
};

export const getMostPollutingCities = (weatherData: WeatherData[]): CityPollution[] => {
  const currentYear = new Date().getFullYear();
  return weatherData
    .filter((data) => new Date(data.dateTime).getFullYear() === currentYear)
    .reduce((acc: CityPollution[], weather) => {
      const cityData = acc.find((city) => city.city === weather.city && city.country === weather.country);
      if (cityData) {
        cityData.carbonMonoxide = Math.max(cityData.carbonMonoxide, weather.carbonMonoxide);
      } else {
        acc.push({ city: weather.city, country: weather.country, carbonMonoxide: weather.carbonMonoxide });
      }
      return acc;
    }, [])
    .sort((a, b) => b.carbonMonoxide - a.carbonMonoxide)
    .slice(0, 11);
};