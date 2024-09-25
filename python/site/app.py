from flask import Flask, render_template
from weather_forecast import WeatherForecast

app = Flask(__name__)

@app.route('/report')
def index():
    weather_forecast = WeatherForecast()
    cities_count = weather_forecast.get_cities_count()
    pollution_cities = weather_forecast.get_most_polluting_cities()
    chart_weather_condition = weather_forecast.create_chart_weather_condition()
    chart_pollution_compare = weather_forecast.create_chart_pollution_compare()
    chart_temperature_groups = weather_forecast.create_chart_temperature_groups()

    return render_template(
        'index.html', 
        cities_count=cities_count,
        pollution_cities=pollution_cities,
        chart_weather_condition=chart_weather_condition, 
        chart_pollution_compare=chart_pollution_compare, 
        chart_temperature_groups=chart_temperature_groups
    )

if __name__ == '__main__':
    from werkzeug.serving import run_simple
    run_simple('localhost', 5000, app)