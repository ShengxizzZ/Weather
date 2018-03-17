package com.shengxi.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.shengxi.bean.CityDataBean;
import com.shengxi.bean.WeatherDataBean;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utills {

	public Utills() {

	}

	public String getData(String path) {

		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				in = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				System.out.println(sb.toString());
				return sb.toString();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (conn != null) {
				conn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public boolean netState(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();

		if(info!=null){
			return info.isAvailable();
		}else{
		
			return false;
		}
	}
	
	public WeatherDataBean paserGson(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, WeatherDataBean.class);
		
	}
	
	public CityDataBean paserCityGson(String json){
		
		Gson gson = new Gson();
		return gson.fromJson(json, CityDataBean.class);
	}
	ProgressDialog pd ;
	public void showProgressDialog(Context context){
		
		 pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("拼命加载。。。。。。");
		pd.setCanceledOnTouchOutside(false);
		pd.show();
	}
	
	
	public void dismissDialog(){
		
		if(pd!=null){
			pd.dismiss();
		}
	}
	
}
