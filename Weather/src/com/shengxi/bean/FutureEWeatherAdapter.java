package com.shengxi.bean;


import com.shengxi.weather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FutureEWeatherAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private WeatherDataBean dataBean;
	public FutureEWeatherAdapter(Context context,WeatherDataBean data) {
		
		if(data !=null){
			this.inflater = LayoutInflater.from(context);
			this.dataBean = data;
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataBean.result.weather.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Item item;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_item, null);
			
			item = new Item();
			item.weather = (ImageView) convertView.findViewById(R.id.today_icon);
			item.date = (TextView) convertView.findViewById(R.id.date);
			item.weatherInfo = (TextView) convertView.findViewById(R.id.today_tianqi);
			item.maxTemp = (TextView) convertView.findViewById(R.id.today_max);
			item.minTemp = (TextView) convertView.findViewById(R.id.today_min);
			item.winf = (TextView) convertView.findViewById(R.id.today_winf);
			item.week = (TextView) convertView.findViewById(R.id.week);
			convertView.setTag(item);	
		}else{
			item = (Item) convertView.getTag();
		}
		
		item.weather.setBackgroundResource(isWeather(dataBean.result.weather.get(position).info.day.get(1)));
	
		if(position==0){
			item.week.setText("今天");
		}else if(position==1){
			item.week.setText("明天");
		}else{
			item.week.setText("周"+dataBean.result.weather.get(position).week);
		}
		item.date.setText(dataBean.result.weather.get(position).date);
		item.maxTemp.setText(dataBean.result.weather.get(position).info.day.get(2)+"°");
		item.minTemp.setText(dataBean.result.weather.get(position).info.night.get(2)+"°");
		item.weatherInfo.setText(dataBean.result.weather.get(position).info.day.get(1));
		item.winf.setText(dataBean.result.weather.get(position).info.day.get(3));
		return convertView;
	}

	
	private int isWeather(String string) {
		
		switch (string) {
		case "雷阵雨":
			return com.shengxi.weather.R.drawable.daily_forecast_t_storm;
			
		case "小雨":
			return com.shengxi.weather.R.drawable.daily_forecast_rain;
		case "中雨":
			return com.shengxi.weather.R.drawable.daily_forecast_rain;
		case "晴":
			return com.shengxi.weather.R.drawable.daily_forecast_sunny;
			
		case "霾":
			return com.shengxi.weather.R.drawable.daily_forecast_pm_dirt;
		case "雾":
			return com.shengxi.weather.R.drawable.daily_forecast_foggy;
		case"晴间多云":
			return com.shengxi.weather.R.drawable.daily_forecast_cloudy;
		case "多云":
			return com.shengxi.weather.R.drawable.daily_forecast_overcast;
			
		case "阵雨":
			return com.shengxi.weather.R.drawable.daily_forecast_rain;
		default:
			return com.shengxi.weather.R.drawable.daily_forecast_overcast;
		}
		// TODO Auto-generated method stub
	}


	private class Item{
		
		private ImageView weather;
		private TextView date;
		private TextView week;
		private TextView weatherInfo;
		private TextView maxTemp;
		private TextView minTemp;
		private TextView winf;
	}
}
