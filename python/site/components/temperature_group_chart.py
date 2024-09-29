import os
import pandas as pd
import matplotlib.pyplot as graphic
import seaborn
from services.weather_service import feach_weather_data

def create_temperature_groups_chart():
    weather_data = feach_weather_data()
    temperature_limits = [-30, -20, -10, 0, 10, 20, 30, 40, 50]
    temperature_group = ['-20 or minus', '-19 to -10', '-9 to 0', '1 to 10', '11 to 20', '21 to 30', '31 to 40', '41+']
    weather_data['temperature_group'] = pd.cut(weather_data.temperature, bins=temperature_limits, labels=temperature_group, right=False)
    temperature_list = (
        weather_data[['temperature_group']]
        .groupby(['temperature_group']).size().reset_index(name='count')
        .sort_values(by=['temperature_group'], ascending=False)
    )
    palette_color = seaborn.color_palette('colorblind')
    fig, ax = graphic.subplots(figsize=(8, 4))
    wedges, texts, autotexts = ax.pie(temperature_list['count'], colors=palette_color, autopct='', startangle=90)
    for text in texts:
        text.set_size(12)
    for autotext in autotexts:
        autotext.set_size(10)
        autotext.set_color('black')
        autotext.set_position((1.2, 0))
    ax.legend(wedges, [f'{temperature}°C - {count}' for temperature, count in zip(temperature_list['temperature_group'], temperature_list['count'])], title="Temperature Groups", loc="center", bbox_to_anchor=(1, 0, 0.5, 1))
    chart_temperature_groups = os.path.join('static', 'chart_temperature_groups.png')
    fig.savefig(chart_temperature_groups)
    graphic.close()
    return chart_temperature_groups