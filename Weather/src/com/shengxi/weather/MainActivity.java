package com.shengxi.weather;

import java.util.ArrayList;
import java.util.List;

import com.shengxi.fragment.CityList;
import com.shengxi.fragment.WeatherFragment;
import com.shengxi.fragment.WeatherInfoFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	
	public static LinearLayout main;
	//private ImageView back,add;
	//private TextView cityName;
	private ViewPager vPage;
	private List<Fragment> list;
	//private MyAdapter myadapter;
	public WeatherFragment weatherFragment;
	public WeatherInfoFragment weatherInfoFragment;
	public CityList cityList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//透明状态栏  
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  
            Window window = getWindow();  
            // Translucent status bar  
            window.setFlags(  
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,  
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
        }  

//        Intent in = getIntent();
//        String value = in.getStringExtra("cityName");
//        Log.e("ssss", value);
       
		
		initView();
		//
//		 if(WeatherFragment.data.result.realtime.weather.info.equals("晴")){
//	        	main.setBackgroundResource(drawable.bg_sunny);
//	        }
		list = new ArrayList<Fragment>();
		weatherFragment = new WeatherFragment();
		weatherInfoFragment = new WeatherInfoFragment();
		list.add(weatherFragment);
		list.add(weatherInfoFragment);
		
		MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
		vPage.setAdapter(adapter);
	}
	private void initView() {
//		back = (ImageView) findViewById(R.id.back);
//		add = (ImageView) findViewById(R.id.add);
//		cityName = (TextView) findViewById(R.id.cityNeme);
		main = (LinearLayout) findViewById(R.id.main);
		vPage = (ViewPager) findViewById(R.id.viewPager);
	}
	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	long first = 0;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		long second  =first;
		first = System.currentTimeMillis();
		
		
		if(second - first <=2000){
			finish();
			
		}else{
			Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
		}
	}
}
