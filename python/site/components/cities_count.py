from services.weather_service import feach_weather_data

def get_cities_count():
    weather_data = feach_weather_data()
    cities_count = len(weather_data.groupby(['city','country']))
    return cities_count
