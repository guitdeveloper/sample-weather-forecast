import os
import matplotlib.pyplot as graphic
import seaborn
from services.weather_service import feach_weather_data

def create_weather_condition_chart():
    weather_data = feach_weather_data()
    description_list = (
        weather_data[weather_data.year == 2024]
        .groupby(['description']).size().reset_index(name='count')
    )
    description_list = (
        description_list[description_list['count'] > 1]
        .sort_values(by=['count'], ascending=False)
    )
    total = description_list['count'].sum()
    description_list['percent'] = (description_list['count'] / total) * 100
    graphic.figure(figsize=(10, 5))
    ax = seaborn.barplot(x='count', y='description', data=description_list, palette='colorblind')
    for index, value in enumerate(description_list['percent']):
        ax.text(value + 1, index, f'{value:.1f}%', va='center')
    graphic.xlabel('Quantity')
    graphic.ylabel('Weather Condition')
    chart_weather_condition = os.path.join('static', 'chart_weather_condition.png')
    graphic.savefig(chart_weather_condition)
    graphic.close()
    return chart_weather_condition

