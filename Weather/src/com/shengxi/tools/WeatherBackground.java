package com.shengxi.tools;

import com.shengxi.bean.WeatherDataBean;

public class WeatherBackground {
	
	private WeatherDataBean data;
	public WeatherBackground(WeatherDataBean data) {
		if(data!=null){
			this.data = data;
		}
	}
	
	/**
	 * 
	 * 设置图片
	 * 
	 * @param info
	 * @return
	 */
	public  int getRight() {
		switch (data.result.realtime.weather.info) {
		case "晴":
			return com.shengxi.weather.R.drawable.bg_sunny_right;
		case "阴":
			return com.shengxi.weather.R.drawable.bg_cloudy_right;
		case "多云":
			return com.shengxi.weather.R.drawable.bg_cloudy_right;
		case "雷阵雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_right;
		case "中雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_right;

		case "小雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_right;
		case "阵雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_right;

		case "霾":
			return com.shengxi.weather.R.drawable.bg_pmdirt_right;
		default:
			break;
		}
		return com.shengxi.weather.R.drawable.bg_cloudy_right;
	}

	
	public int getNumber(int i) {

		switch (i) {
		case 0:
			return com.shengxi.weather.R.drawable.num0;
		case 1:
			return com.shengxi.weather.R.drawable.num1;
		case 2:
			return com.shengxi.weather.R.drawable.num2;
		case 3:
			return com.shengxi.weather.R.drawable.num3;
		case 4:
			return com.shengxi.weather.R.drawable.num4;
		case 5:
			return com.shengxi.weather.R.drawable.num5;
		case 6:
			return com.shengxi.weather.R.drawable.num6;
		case 7:
			return com.shengxi.weather.R.drawable.num7;
		case 8:
			return com.shengxi.weather.R.drawable.num8;
		case 9:
			return com.shengxi.weather.R.drawable.num9;
		default:
			return 0;
		}
	}

	public int getLeft () {
		switch (data.result.realtime.weather.info) {
		case "晴":
			return com.shengxi.weather.R.drawable.bg_sunny_left;
		case "阴":
			return com.shengxi.weather.R.drawable.bg_cloudy_left;
		case "多云":
			return com.shengxi.weather.R.drawable.bg_cloudy_left;
		case "雷阵雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_left;
		case "中雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_left;

		case "小雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_left;
		case "阵雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain_left;
		case "霾":
			return com.shengxi.weather.R.drawable.bg_pmdirt_left;
		default:
			break;
		}
		return com.shengxi.weather.R.drawable.bg_cloudy_left;
	}

	public int getWeather() {
		
		switch (data.result.realtime.weather.info) {
		case "晴":
			return com.shengxi.weather.R.drawable.bg_sunny;

		case "阴":
			return com.shengxi.weather.R.drawable.bg_cloudy;

		case "小雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain;
		case "中雨":
		
			return com.shengxi.weather.R.drawable.bg_ice_rain;
		case "阵雨":
			
			return com.shengxi.weather.R.drawable.bg_ice_rain;
		case "多云":
			return com.shengxi.weather.R.drawable.bg_cloudy;
		case "雷阵雨":
			return com.shengxi.weather.R.drawable.bg_ice_rain;
		case "霾":
			return com.shengxi.weather.R.drawable.bg_pmdirt;
		default:
			return com.shengxi.weather.R.drawable.bg_cloudy;
		}

	}
}
