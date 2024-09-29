from services.weather_service import feach_weather_data

def get_most_polluting_cities():
    weather_data = feach_weather_data()
    pollution_city = (
        weather_data[weather_data.year == 2024]
        .groupby(['city','country']).carbonMonoxide.max()
        .reset_index()
        .sort_values(by=['carbonMonoxide'], ascending=False)
    )
    return pollution_city.head(11).itertuples()