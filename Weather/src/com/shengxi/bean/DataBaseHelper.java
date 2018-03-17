package com.shengxi.bean;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

	/**
	 * 
	 * @param context 上下文环境
	 * @param name 数据库名
	 * @param factory 启用参数，传null
	 * @param version  数据库版本（int）
	 * @param errorHandler
	 */
	
	public DataBaseHelper(Context context) {
		super(context, "city_db",null,1);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 数据库第一次调用时调用这个方法
	 * 
	 * 创建数据库表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//渐变
		db.execSQL(WeatherFinal.citySql);
		db.execSQL(WeatherFinal.addCitySql);
		//执行建表语句
		
		
		
	}

	/**
	 * 
	 * 数据库更新的时候调用这个方法
	 * 
	 * 当版本大于本版版本就会自动调用这个函数
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
