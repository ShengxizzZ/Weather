package com.shengxi.bean;

import java.io.Serializable;
import java.util.List;


public class CityDataBean implements Serializable{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public List<CityInfo> city_info;
	public class CityInfo{
		public String city;
		public String id;
		public String lat;
		public String lon;
		public String prov;
	}

}
