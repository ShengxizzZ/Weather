package com.shengxi.bean;

import java.io.Serializable;
import java.util.List;

public class WeatherDataBean implements Serializable{

public String reason;//返回请求成功，或者是失败的原因
	
	public Reslut result;
	public static class Reslut{
		public Realtime realtime;
		public static class Realtime{
			public String date;//当天的日期
			public String time;//更新的时间
			public String city_name;//当前的城市名称
			public String week;//星期几
			public String moon;//农历日期
			
			public Wind wind;
			public static class Wind{
				public String direct;//风向
				public String power;//风力
			}
			
			public Weather weather;
			public static class Weather{
				public String info; //天气情况
				public String temperature;//当前的温度
				public String humidity;//湿度
			}
		}
		public Life life;//生活指数
		public static class Life{
			public Info info;//生活指数的info
			public static class Info{
				public List<String> kongtiao;
				public List<String> yundong;
				public List<String> ziwaixian;
				public List<String> ganmao;
				public List<String> xiche;
				public List<String> wuran;
				public List<String> chuanyi;
			}
		}
		
		public List<FutureWeather> weather;//未来几天的天气数组的key
		public static class FutureWeather{
			public String date;//未来几天天气的日期
			public String week;//未来几天天气的星期几
			public String nongli;//未来几天天气的农历日期
			public Info info;//未来几天天气的info
			public static class Info{
				public List<String> day; //白天的天气信息
				public List<String> night;//晚上的天气信息
			}
		}
		public Pm25 pm25;
		public static class Pm25{
			public String dateTime;
			public Pm250 pm25;
			public static class Pm250{
				public String curPm;//当前pm2.5的值
				public String quality;//空气情况
				public String des; // 提示
				public String pm25;
				public String pm10;
				public String level;
			}
		}
	}
}
