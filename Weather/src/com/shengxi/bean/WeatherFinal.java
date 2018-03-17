package com.shengxi.bean;

public class WeatherFinal {

	public static final String path = "http://api.avatardata.cn/Weather/Query?key=7bc0453b85c6446aa48edcb6e81d087e&cityname=";
	public static final String cityPath = "https://api.heweather.com/x3/citylist?search=allchina&key=4f1e32febf3548e29b76c91aa7652710";
	public static final String citySql = "create table city(_id integer primary key autoincrement,cityName text)";
	public static final String addCitySql = "create table addcity(_id integer primary key autoincrement,cityName text)";
}