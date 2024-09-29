from pymongo import MongoClient
import pandas as pd

def feach_weather_data():
    client = MongoClient("mongodb://localhost:27017/")
    db = client['weatherdb']
    collection = db['weathers']
    data = list(collection.find())
    df = pd.DataFrame(data)
    df = df.drop(columns=['_id', '_class']).dropna()
    df['year'] = df.dateTime.dt.year
    return df