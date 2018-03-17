package com.shengxi.bean;

import java.util.List;

import com.shengxi.weather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<GridCityInfo> data;
	
	public GridAdapter(Context context,List<GridCityInfo> list) {
		
		if(data != null){
			this.inflater = LayoutInflater.from(context);
			this.data = list;
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Item item;
		if(convertView == null){
			
			item = new Item();
			convertView = inflater.inflate(R.layout.city_list_itme, null);
			//item.cityName = (TextView) convertView.findViewById(R.id.grid_cityname);
			//item.Info = (TextView) convertView.findViewById(R.id.grid_weather_info);
			//item.weather = (ImageView) convertView.findViewById(R.id.grid_image);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		//item.cityName = (TextView) convertView.findViewById(R.id.grid_cityname);
		//item.Info = (TextView) convertView.findViewById(R.id.grid_weather_info);
		
		return convertView;
	}

	private class Item{
		
		private TextView cityName;
		private TextView Info;
		private ImageView weather;
	}
}
