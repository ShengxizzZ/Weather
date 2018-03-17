package com.shengxi.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shengxi.bean.DataBaseHelper;
import com.shengxi.bean.GridCityInfo;
import com.shengxi.bean.WeatherDataBean;
import com.shengxi.tools.Utills;
import com.shengxi.tools.WeatherBackground;
import com.shengxi.weather.MainActivity;
import com.shengxi.weather.R;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 创建数据库的帮助类 通过帮助类得到数据库的操作类
 * 
 * @author ShengXi
 *
 */
public class CityList extends Activity {

	// 定义数据库的帮助类
	DataBaseHelper dbHelper;

	private GridView gv;
	private List<String> list;
	private ArrayAdapter<String> adapter;
	private ImageView addCity;
	private EditText dialog_ed, cityLisyEd;
	private Dialog dialog;
	private RelativeLayout reMain;

	private ImageView cityListAdd;
	private ImageView back;
	WeatherBackground bg;

	private List<GridCityInfo> gridViewList;
	private GridCityInfo gridCityInfo;
	Context context;
	private Utills utills;
	private WeatherDataBean dataBean;

	public static String cityName = "北京";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list);
		// 透明状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

		context = getApplicationContext();
		dbHelper = new DataBaseHelper(this);
		reMain = (RelativeLayout) findViewById(R.id.city_list_main);
		back = (ImageView) findViewById(R.id.city_back);
		cityListAdd = (ImageView) findViewById(R.id.city_list_add);
		gv = (GridView) findViewById(R.id.citymanager_gv);
		cityLisyEd = (EditText) findViewById(R.id.city_list_ed_add);

		bg = new WeatherBackground(WeatherFragment.data);
		if (WeatherFragment.data != null) {
			reMain.setBackgroundResource(bg.getWeather());
		}

		getCityNameFromSQL();

		adapter = new ArrayAdapter<String>(CityList.this, R.layout.city_list_itme, R.id.grid_cityname, list);
		gv.setAdapter(adapter);

		/**
		 * 长按监听
		 */
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				deleteCityNameToSQL(arg0.getItemAtPosition(arg2).toString());
				getCityNameFromSQL();

				adapter = new ArrayAdapter<String>(CityList.this, R.layout.city_list_itme, R.id.grid_cityname,
						list);
				gv.setAdapter(adapter);
				return false;
			}
		});
		
		/**
		 * 点击监听
		 */
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				Log.e("ssssssssss", arg0.getItemAtPosition(arg2).toString());
				//listener.setDataCity(arg0.getItemAtPosition(arg2).toString());
				cityName = arg0.getItemAtPosition(arg2).toString();
				//Toast.makeText(context, "点击了" + , Toast.LENGTH_SHORT).show();
				Intent in = new Intent(CityList.this, MainActivity.class);
				//in.putExtra("cityNmame",arg0.getItemAtPosition(arg2).toString());
				CityList.this.startActivity(in);
				finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		cityListAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String cityName = cityLisyEd.getText().toString();

				if (isRightCity(cityName) && isRightAddCity(cityName)) {
					cityLisyEd.setText("");
					//gotoWeat
					putCityNameToSQL(cityName);
					getCityNameFromSQL();
					
					//gotoThread();
					adapter = new ArrayAdapter<String>(CityList.this, R.layout.city_list_itme, R.id.grid_cityname,
							list);
					gv.setAdapter(adapter);
				} else {
					Toast.makeText(context, "输入正确的城市", Toast.LENGTH_SHORT).show();
				}

			}

			

			private boolean isRightAddCity(String cityName) {
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				// SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor curser = db.rawQuery("select *from addcity where cityName=?", new String[] { cityName });
				int i = 0;
				while (curser.moveToNext()) {
					i++;
				}
				curser.close();
				db.close();
				if (i == 0) {
					return true;
				} else {
					return false;
				}
			}

			private boolean isRightCity(String cityName) {

				SQLiteDatabase db = dbHelper.getReadableDatabase();
				// SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor curser = db.rawQuery("select *from city where cityName=?", new String[] { cityName });
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
		});
	}
	
	private void gotoThread() {
		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					for(int i = 0;i<list.size();i++){
						String result = utills.getData(list.get(i));
						if(result!=null){
							
							dataBean = utills.paserGson(result);
							gridCityInfo = new GridCityInfo();
							gridCityInfo.setCityName(dataBean.result.realtime.city_name);
							gridCityInfo.setInfo(dataBean.result.realtime.weather.info);
							gridViewList.add(gridCityInfo);
		
						}else{
							
							
						}
						
					}
					
				}
			}).start();
		
	}

	protected void showDialog() {

		dialog = new Dialog(this);
		// 动态布局加载器
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.mydialog, null);
		dialog.setContentView(view);
		dialog.setTitle("请输入城市：");

		dialog_ed = (EditText) view.findViewById(R.id.ed);
		view.findViewById(R.id.dialog_ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				String cityName = dialog_ed.getText().toString();

				putCityNameToSQL(cityName);
				getCityNameFromSQL();

				adapter = new ArrayAdapter<>(CityList.this, R.layout.city_list_itme, R.id.grid_cityname, list);
				gv.setAdapter(adapter);

			}
		});
		dialog.show();
	}

	public void putCityNameToSQL(String city) {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ContentValues v = new ContentValues();
		v.put("cityName", city);
		db.insert("addcity", null, v);
		db.close();
	}
	
	public void deleteCityNameToSQL(String city){
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete("addcity", "cityName=?", new String []{city});
		db.close();
	}

	public void getCityNameFromSQL() {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		list = new ArrayList<String>();
		Cursor cur = db.rawQuery("select *from addcity", null);
		while (cur.moveToNext()) {

			String cityName = cur.getString(cur.getColumnIndex("cityName"));
			list.add(cityName);
		}
		db.close();
	}
//	OnDataCityListener listener;
//	public interface OnDataCityListener{
//		
//		void setDataCity(String str);
//	}
//	
//	public void setOnDataCityListener(OnDataCityListener listener){
//		
//		if(this.listener == null){
//			this.listener = listener;
//		}
//	}
}
