package com.shengxi.weather;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.shengxi.bean.CityDataBean;
import com.shengxi.bean.DataBaseHelper;
import com.shengxi.bean.WeatherFinal;
import com.shengxi.tools.Utills;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class LogoActivity extends Activity {

	CityDataBean data;
	// SharedPreferences 存储在app本身，一xml进行存储
	// 支持文件存储 内部存储，外部存储 --- 缓存LruCacha
	//
	private ImageView image;

	private SharedPreferences sp;// 静态内
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.logo);

		image = (ImageView) findViewById(R.id.image_view);

		sp = getSharedPreferences("isFirst", MODE_PRIVATE);// name:xml文件名、mode:
		// 先从文件中获取是否是第一次运行
		boolean isfirst = sp.getBoolean("first", true);

		if (isfirst) {
			editor = sp.edit();
			editor.putBoolean("first", false);
			// 提交
			editor.apply();

			// 使用动画效果
			// 改变透明度、平移
			// 定义一个渐变动画 透明度值0~1
			AlphaAnimation aa = new AlphaAnimation(0.5f, 1);
			aa.setDuration(1000);

			image.startAnimation(aa);
			aa.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					Intent in = new Intent();
					in.setClass(LogoActivity.this, MainActivity.class);
					startActivity(in);
					finish();
				}
			});
			
			
//			try {
//				//文件流存储
//				FileOutputStream fos = openFileOutput("weather.abc", MODE_PRIVATE);
//				//写
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
//				
//				//oos.writeObject(object);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else{
			Intent in = new Intent();
			in.setClass(LogoActivity.this, MainActivity.class);
			startActivity(in);
			finish();
		}
	}

}
