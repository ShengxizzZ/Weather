package com.shengxi.fragment;

import com.shengxi.bean.LifeAdapter;
import com.shengxi.bean.WeatherDataBean;
import com.shengxi.fragment.WeatherFragment.OnDataChangedListener;
import com.shengxi.weather.MainActivity;
import com.shengxi.weather.R;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class WeatherInfoFragment extends Fragment implements OnDataChangedListener{

	public static LinearLayout title;
	public static TextView cityName;
	public static TextView deTime;
	public static TextView infoKongqi;
	public static TextView pm25;
	public static TextView pm10;
	public static TextView level;
	public static TextView curPm;
	public static TextView des;
	public static ListView list;
	public static LifeAdapter lifeAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.weatherinfofragment, null);
		initView(view);
		// 注册方波
		//getActivity().registerReceiver(  lbr, new IntentFilter("weather"));
		MainActivity mainActivity = (MainActivity) getActivity();
		
		mainActivity.weatherFragment.setOnDataChangedListener(this);
		
		return view;
	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			WeatherDataBean dataBean = (WeatherDataBean) msg.obj;
			if(msg.what == 1){
				//更新Ui
				
			}
			
		}
	};
	private void initView(View view) {

		title = (LinearLayout) view.findViewById(R.id.title);
		cityName = (TextView) view.findViewById(R.id.info_cityName);
		deTime = (TextView) view.findViewById(R.id.description);
		infoKongqi = (TextView) view.findViewById(R.id.info_quality);
		pm25 = (TextView) view.findViewById(R.id.pm25);
		pm10 = (TextView) view.findViewById(R.id.pm10);
		level = (TextView) view.findViewById(R.id.level);
		curPm = (TextView) view.findViewById(R.id.curPm);
		des = (TextView) view.findViewById(R.id.des);
		list = (ListView) view.findViewById(R.id.infoListView);

	}

//	// 定义一个广播接收器
//	private BroadcastReceiver br = new BroadcastReceiver() {
//		public void onReceive(android.content.Context context, android.content.Intent intent) {
//			if (intent.getAction().equals("weather")) {
//				Bundle b = intent.getBundleExtra("bundle");
//				WeatherDataBean dataBean = (WeatherDataBean) b.get(getResultData());
//				if (dataBean != null) {
//					// UI操作
//
//				}
//			}
//		};
//
//	};

	@Override
	public void OnDataChanged(WeatherDataBean data) {
		// TODO Auto-generated method stub
		//发送消息
		Message msg = new Message();
		msg.what = 1;
		msg.obj = data;
		handler.sendMessage(msg);
	}
}
