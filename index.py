import pandas as pd
import quandl

quandl.ApiConfig.api_key = 'Z7h-s84v8y2yMti5s1xB'

df=quandl.get_table('WIKI/PRICES')

df = df[['adj_open',  'adj_high','adj_low','adj_close','adj_volume']]

df['HL_PCT'] = (df['adj_high'] - df['adj_low']) / df['adj_close'] * 100.0

df['PCT_change'] = (df['adj_close'] - df['adj_open']) / df['adj_open'] * 100.0

df = df[['adj_close', 'HL_PCT', 'PCT_change', 'adj_volume']]

print(df.head())
