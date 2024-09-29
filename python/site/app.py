from flask import Flask, render_template
from components.cities_count import get_cities_count
from components.most_polluting_cities import get_most_polluting_cities
from components.weather_condition_chart import create_weather_condition_chart
from components.pollution_comparison_chart import create_pollution_compare_chart
from components.temperature_group_chart import create_temperature_groups_chart

app = Flask(__name__)

@app.route('/report')
def index():
    cities_count = get_cities_count()
    pollution_cities = get_most_polluting_cities()
    chart_weather_condition = create_weather_condition_chart()
    chart_pollution_compare = create_pollution_compare_chart()
    chart_temperature_groups = create_temperature_groups_chart()
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