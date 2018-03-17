package com.shengxi.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.shengxi.bean.CityDataBean;
import com.shengxi.bean.DataBaseHelper;
import com.shengxi.bean.FutureEWeatherAdapter;
import com.shengxi.bean.LifeAdapter;
import com.shengxi.bean.WeatherDataBean;
import com.shengxi.bean.WeatherFinal;
import com.shengxi.weather.MainActivity;
import com.shengxi.weather.R;
import com.shengxi.tools.Utills;
import com.shengxi.tools.WeatherBackground;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherFragment extends Fragment {

	private ListView list;
	private LinearLayout show, bgLeft, bgRight;
	private TextView cityName;
	private TextView tianqi;
	private TextView windDirect;
	private TextView windPower;
	private TextView humidity;
	private TextView quality;

	private ImageView tempLeft;
	private ImageView tempRight;

	private Dialog dialog;
	private ImageView updata;
	private ImageView addCity;
	private String city = "成都";
	private Utills utills;
	public static WeatherDataBean data;
	public static CityDataBean cityData;
	private EditText dialog_ed;
	private TextView dialog_tv;

	PopupMenu piu;
	Menu menu;
	DataBaseHelper dbHelper;
	WeatherBackground bg;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {

				Toast.makeText(getActivity(), "获取数据成功", Toast.LENGTH_SHORT).show();
				setDataToView();
			} else if (msg.what == 2) {
				Toast.makeText(getActivity(), "未知错误", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 4) {
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
				setDataToView();
			} 
			utills.dismissDialog();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view = inflater.inflate(R.layout.weatherfragment, null);
		}
		dbHelper = new DataBaseHelper(getContext());
		initView(view);
		
		if(!city.equals(CityList.cityName)){
			city = CityList.cityName;
		}
		
		// utills.getData(WeatherFinal.path+city);
		// 联网获取数据
		
		utills = new Utills();
		utills.showProgressDialog(getActivity());
		if (utills.netState(getActivity())) {
			// 子线程获取数据
			if(!isReight()){
				gotoGetCityListThread();
			}
			gotoChThread();
		} else {
			utills.dismissDialog();
			Toast.makeText(getActivity(), "无网络连接", Toast.LENGTH_SHORT).show();
		}
		
		return view;
	}

	private void gotoGetCityListThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				String result = utills.getData(WeatherFinal.cityPath);
				if (result == null) {

					handler.sendEmptyMessage(3);
				} else {
					cityData = utills.paserCityGson(result);
					for (int i = 0; i < cityData.city_info.size(); i++) {
						SQLiteDatabase db = dbHelper.getReadableDatabase();
						ContentValues values = new ContentValues();
						values.put("cityName", cityData.city_info.get(i).city);
						db.insert("city", null, values);
						db.close();
					}
					Log.e("out", cityData.toString());
					//handler.sendEmptyMessage(5);
				}
			}
		}).start();
	}

	private void updataThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					city = URLEncoder.encode(city, "utf-8");
					String result = utills.getData(WeatherFinal.path + city);
					if (result == null) {
						handler.sendEmptyMessage(3);
					} else {
						// 解析json 到data
						data = utills.paserGson(result);
						handler.sendEmptyMessage(4);
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	private void gotoChThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// city = URLEncoder.encode(city, "utf-8");
				String result = utills.getData(WeatherFinal.path + city);
				if (result == null) {
					handler.sendEmptyMessage(2);
				} else {
					// 解析J son 到data
					data = utills.paserGson(result);
					// Bundle b = new Bundle();
					listener.OnDataChanged(data);
					handler.sendEmptyMessage(1);
				}
			}
		}).start();

	}

	/**
	 * 给控件赋值
	 */
	private void setDataToView() {

		if (data.result != null) {
			FutureEWeatherAdapter adapter = new FutureEWeatherAdapter(getContext(), data);
			list.setAdapter(adapter);
			bg = new WeatherBackground(data);

			int temp = Integer.parseInt(data.result.realtime.weather.temperature);
			tempLeft.setBackgroundResource(bg.getNumber(temp / 10));
			tempRight.setBackgroundResource(bg.getNumber(temp % 10));

			cityName.setText(data.result.realtime.city_name);
			tianqi.setText(data.result.realtime.weather.info);
			windDirect.setText(data.result.realtime.wind.direct);
			windPower.setText(data.result.realtime.wind.power);
			humidity.setText(data.result.realtime.weather.humidity + "%");
			quality.setText(data.result.pm25.pm25.quality);

			AlphaAnimation aa = new AlphaAnimation(0, 1);
			aa.setDuration(3 * 1000);
			show.setBackgroundResource(bg.getWeather());
			MainActivity.main.setBackgroundResource(bg.getWeather());
			WeatherInfoFragment.title.setBackgroundResource(bg.getWeather());
			bgLeft.setBackgroundResource(bg.getLeft());
			bgRight.setBackgroundResource(bg.getRight());
			tempRight.startAnimation(aa);
			tempLeft.startAnimation(aa);

			TranslateAnimation animation = new TranslateAnimation(0, 0, 10, 0);
			TranslateAnimation ani = new TranslateAnimation(0, 0, 0, 10);
			ani.setDuration(1000);
			ani.setRepeatCount(50);
			ani.setRepeatMode(Animation.REVERSE);

			animation.setDuration(1000);
			animation.setRepeatCount(50);
			animation.setRepeatMode(Animation.REVERSE);
			bgLeft.setAnimation(ani);
			bgRight.setAnimation(animation);
			animation.startNow();
			ani.startNow();

			// 几天天气预报
			// System.out.println(data.result.weather.get(0).info.day.get(0).charAt(2));
			WeatherInfoFragment.cityName.setText(data.result.realtime.city_name);
			WeatherInfoFragment.deTime.setText("阿凡达天气" + data.result.pm25.dateTime + "发布");
			WeatherInfoFragment.curPm.setText(data.result.pm25.pm25.curPm);
			WeatherInfoFragment.des.setText(data.result.pm25.pm25.des);
			WeatherInfoFragment.pm10.setText(data.result.pm25.pm25.pm10);
			WeatherInfoFragment.pm25.setText(data.result.pm25.pm25.pm25);
			WeatherInfoFragment.level.setText(data.result.pm25.pm25.level);
			WeatherInfoFragment.infoKongqi.setText(data.result.pm25.pm25.quality);

			WeatherInfoFragment.lifeAdapter = new LifeAdapter(getContext(), data);
			WeatherInfoFragment.list.setAdapter(WeatherInfoFragment.lifeAdapter);
		} else {
			Log.e("ss", "city 空");
		}

	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void initView(View view) {

		cityName = (TextView) view.findViewById(R.id.cityName);
		windDirect = (TextView) view.findViewById(R.id.winf);
		tianqi = (TextView) view.findViewById(R.id.tianqi);
		windPower = (TextView) view.findViewById(R.id.winPower);
		humidity = (TextView) view.findViewById(R.id.shiduNum);
		quality = (TextView) view.findViewById(R.id.quality);
		tempLeft = (ImageView) view.findViewById(R.id.num_left);
		tempRight = (ImageView) view.findViewById(R.id.num_right);
		show = (LinearLayout) view.findViewById(R.id.show);
		bgLeft = (LinearLayout) view.findViewById(R.id.bg_left);
		bgRight = (LinearLayout) view.findViewById(R.id.bg_right);
		list = (ListView) view.findViewById(R.id.listView);

		addCity = (ImageView) view.findViewById(R.id.addCity);
		updata = (ImageView) view.findViewById(R.id.updata);
		addCity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog = new Dialog(getActivity(), R.style.Dialog);
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				View view = inflater.inflate(R.layout.mydialog1, null);
				dialog.setContentView(view);

				Window dialogWind = dialog.getWindow();
				WindowManager.LayoutParams lp = dialogWind.getAttributes();
				dialogWind.setGravity(Gravity.RIGHT | Gravity.TOP);
				lp.x = 100; // 新位置X坐标
				lp.y = 100; // 新位置Y坐标
				// lp.width = 300; // 宽度
				// lp.height = 300; // 高度
				lp.alpha = 1; // 透明度
				dialogWind.setAttributes(lp);

				view.findViewById(R.id.add).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Intent in = new Intent(getActivity(), CityList.class);
						WeatherFragment.this.startActivity(in);
						
					}
				});

				view.findViewById(R.id.search).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						showDialog();
					}
				});
				dialog.show();
				// showDialog();
				// Intent in = new Intent(getContext(), CityList.class);
				// WeatherFragment.this.startActivity(in);
			}
		});

		updata.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RotateAnimation ra = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				ra.setDuration(3000);
				updata.setAnimation(ra);
				ra.startNow();

				if(!isReight()){
					gotoGetCityListThread();
				}
				gotoChThread();
			}
		});
	}

	/**
	 * 显示搜索对话框
	 */
	private void showDialog() {

		dialog = new Dialog(getContext(), R.style.Dialog);
		// 动态布局加载器
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.mydialog, null);
		dialog.setContentView(view);

		dialog_ed = (EditText) view.findViewById(R.id.ed);
		dialog_tv = (TextView) view.findViewById(R.id.tv_title);
		dialog_tv.setText("请输入城市名：");
		if (data != null) {
			dialog_tv.setBackgroundResource(bg.getWeather());
		} else {
			dialog_tv.setBackgroundResource(com.shengxi.weather.R.drawable.bg_cloudy);
		}

		view.findViewById(R.id.dialog_ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				city = dialog_ed.getText().toString();
				if (isReight(city)) {
					updataThread();
					dialog.dismiss();
				} else {
					Toast.makeText(getActivity(), "重新输入正确的城市", Toast.LENGTH_SHORT).show();
					dialog_ed.setText("");
				}
			}
		});

		view.findViewById(R.id.dialog_canll).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * 判断输入城市是否正确
	 * 
	 * @param city
	 *            需要判断的城市
	 * @return
	 */
	private boolean isReight(String city) {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor curser = db.rawQuery("select *from city where cityname=?", new String[] { city });
		int i = 0;
		while (curser.moveToNext()) {
			i++;
		}
		curser.close();
		db.close();
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isReight() {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor curser = db.rawQuery("select *from city", null);
		int i = 0;
		while (curser.moveToNext()) {
			i++;
		}
		curser.close();
		db.close();
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}
	/* --------------------------实现接口回调传参------------------------------- */
	// 定义一个回调借口
	public interface OnDataChangedListener {
		// 定义一个回调方法
		void OnDataChanged(WeatherDataBean data);
	}

	OnDataChangedListener listener;

	public void setOnDataChangedListener(OnDataChangedListener litener) {
		if (this.listener == null) {
			this.listener = litener;
		}
	}
}
