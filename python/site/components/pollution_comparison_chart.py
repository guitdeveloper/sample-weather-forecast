import os
import matplotlib.pyplot as graphic
import seaborn
from services.weather_service import feach_weather_data

def create_pollution_compare_chart():
    weather_data = feach_weather_data()
    cities_evolution_pollution = (
        weather_data[weather_data['city'].isin(['São Paulo','Moscow','Delhi','Jakarta'])]
    )
    cities_evolution_pollution = (
        cities_evolution_pollution[['city', 'carbonMonoxide', 'dateTime']]
        .sort_values(by=['carbonMonoxide'], ascending=False)
    )
    graphic.figure(figsize=(6, 5))
    seaborn.lineplot(data=cities_evolution_pollution, x='dateTime', y='carbonMonoxide', hue='city', marker='o', linewidth=3)
    graphic.gca().xaxis.set_major_locator(graphic.matplotlib.dates.HourLocator(interval=12))
    graphic.gca().xaxis.set_major_formatter(graphic.matplotlib.dates.DateFormatter('%d/%m %H:%M'))
    graphic.gcf().autofmt_xdate()
    handles, labels = graphic.gca().get_legend_handles_labels()
    for i, city in enumerate(cities_evolution_pollution['city'].unique()):
        last_value = cities_evolution_pollution[cities_evolution_pollution['city'] == city].iloc[0]['carbonMonoxide']
        labels[i] = f"{labels[i]}: {last_value} μg/m3"
    graphic.legend(handles, labels, title='City')
    graphic.xticks(rotation=30)
    graphic.xlabel('Date')
    graphic.ylabel('Carbon Monoxide (μg/m3)')
    graphic.tight_layout()
    chart_pollution_compare = os.path.join('static', 'chart_pollution_compare.png')
    graphic.savefig(chart_pollution_compare)
    graphic.close()
    return chart_pollution_compare
