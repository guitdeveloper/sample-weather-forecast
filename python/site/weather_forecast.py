from pymongo import MongoClient
import pandas as pd
import os
import matplotlib.pyplot as graphic
import seaborn
import warnings
warnings.simplefilter(action='ignore', category=FutureWarning)

class WeatherForecast:
    def __init__(self):
        client = MongoClient("mongodb://localhost:27017/")
        db = client['weatherdb']
        collection = db['weathers']
        data = list(collection.find())
        df = pd.DataFrame(data)
        df = df.drop(columns=['_id', '_class'])
        df = df.dropna()
        df['year'] = df.dateTime.dt.year
        self.weather_data = df

    def get_cities_count(self):
        cities_count = len(self.weather_data.groupby(['city','country']))
        return cities_count

    def get_most_polluting_cities(self):
        pollution_city = self.weather_data[self.weather_data.year == 2024].groupby(['city','country']).carbonMonoxide.max().reset_index()
        pollution_city = pollution_city.sort_values(by=['carbonMonoxide'], ascending=False)
        return pollution_city.head(11).itertuples()

    def create_chart_weather_condition(self):
        description_list = self.weather_data[self.weather_data.year == 2024].groupby(['description']).size().reset_index(name='count')
        description_list = description_list[description_list['count'] > 1].sort_values(by=['count'], ascending=False)
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

    def create_chart_pollution_compare(self):
        cities_evolution_pollution = self.weather_data[self.weather_data['city'].isin(['São Paulo','Moscow','Delhi','Jakarta'])]
        cities_evolution_pollution = cities_evolution_pollution[['city', 'carbonMonoxide', 'dateTime']].sort_values(by=['carbonMonoxide'], ascending=False)
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

    def create_chart_temperature_groups(self):
        temperature_limits = [-30, -20, -10, 0, 10, 20, 30, 40, 50]
        temperature_group = ['-20 or minus', '-19 to -10', '-9 to 0', '1 to 10', '11 to 20', '21 to 30', '31 to 40', '41+']
        self.weather_data['temperature_group'] = pd.cut(self.weather_data.temperature, bins=temperature_limits, labels=temperature_group, right=False)
        temperature_list = self.weather_data[['temperature_group']].groupby(['temperature_group']).size().reset_index(name='count')
        temperature_list = temperature_list.sort_values(by=['temperature_group'], ascending=False)
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