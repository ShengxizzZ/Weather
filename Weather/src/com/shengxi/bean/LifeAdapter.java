package com.shengxi.bean;

import java.util.ArrayList;
import java.util.List;

import com.shengxi.tools.WeatherBackground;
import com.shengxi.weather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LifeAdapter extends BaseAdapter {

	private LayoutInflater in;
	private WeatherDataBean dataBean;

	private List<Life> list;
	private Life life;

	WeatherBackground bg;
	public LifeAdapter(Context context, WeatherDataBean data) {

		this.in = LayoutInflater.from(context);
		this.dataBean = data;

		list = new ArrayList<>();
		life = new Life();
		life.title = "空调";
		life.ad = dataBean.result.life.info.kongtiao.get(0);
		life.description = dataBean.result.life.info.kongtiao.get(1);
		list.add(life);
		life = null;

		life = new Life();
		life.title = "运动";
		life.ad = dataBean.result.life.info.yundong.get(0);
		life.description = dataBean.result.life.info.yundong.get(1);
		list.add(life);
		life = null;

		life = new Life();
		life.title = "紫外线";
		life.ad = dataBean.result.life.info.ziwaixian.get(0);
		life.description = dataBean.result.life.info.ziwaixian.get(1);
		list.add(life);
		life = null;

		life = new Life();
		life.title = "感冒";
		life.ad = dataBean.result.life.info.ganmao.get(0);
		life.description = dataBean.result.life.info.ganmao.get(1);
		list.add(life);
		life = null;

		life = new Life();
		life.title = "洗车";
		life.ad = dataBean.result.life.info.xiche.get(0);
		life.description = dataBean.result.life.info.xiche.get(1);
		list.add(life);
		life = null;

		life = new Life();
		life.title = "污染";
		life.ad = dataBean.result.life.info.wuran.get(0);
		life.description = dataBean.result.life.info.wuran.get(1);
		list.add(life);
		life = null;

		life = new Life();
		life.title = "穿衣";
		life.ad = dataBean.result.life.info.chuanyi.get(0);
		life.description = dataBean.result.life.info.chuanyi.get(1);
		list.add(life);
		life = null;

		bg = new WeatherBackground(dataBean);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Item item;
		if (convertView == null) {
			convertView = in.inflate(R.layout.life, null);
			item = new Item();
			item.k = (TextView) convertView.findViewById(R.id.k);
			item.kAd = (TextView) convertView.findViewById(R.id.kAd);
			item.kDes = (TextView) convertView.findViewById(R.id.kDes);
			item.li = (RelativeLayout) convertView.findViewById(R.id.reTitle);

			// item.y = (TextView) convertView.findViewById(R.id.y);
			// item.yAd = (TextView) convertView.findViewById(R.id.yAd);
			// item.yDes = (TextView) convertView.findViewById(R.id.yDes);
			convertView.setTag(item);
		} else {

			item = (Item) convertView.getTag();
		}
		item.k.setText(list.get(position).title);
		item.kAd.setText(list.get(position).ad);
		item.kDes.setText(list.get(position).description);

		item.li.setBackgroundResource(bg.getWeather());
		// item.y.setText("运动：");
		// item.yAd.setText(dataBean.result.life.info.yundong.get(0));
		// item.yDes.setText(dataBean.result.life.info.yundong.get(1));

		return convertView;
	}

	private class Item {
		
		private RelativeLayout li;
		private TextView k;
		private TextView kAd;
		private TextView kDes;

		// private TextView z;
		// private TextView zAd;
		// private TextView zDes;
		//
		// private TextView w;
		// private TextView wAd;
		// private TextView wDes;
		//
		// private TextView c;
		// private TextView cAd;
		// private TextView cDes;
		//
		// private TextView g;
		// private TextView gAd;
		// private TextView gDes;
		//
		// private TextView x;
		// private TextView xAd;
		// private TextView xDes;
		//
		// private TextView y;
		// private TextView yAd;
		// private TextView yDes;
	}

	private class Life {

		private String title;
		private String ad;
		private String description;

	}
}
