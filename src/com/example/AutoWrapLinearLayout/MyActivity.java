package com.example.AutoWrapLinearLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;

public class MyActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initTag();
	}
	private void initTag(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("复古");
		list.add("IT民工");
		list.add("文艺");
		list.add("贾静雯迷");
		list.add("恋爱ING");
		list.add("文艺");
		list.add("大大咧咧");
		list.add("颓废ING");
		list.add("攒钱ING");
		list.add("完美主义");
		list.add("薇迷");
		list.add("日剧");
		list.add("Justin迷");
		list.add("柔情似水");
		list.add("爬山");
		TextView tagView;
		LayoutInflater mLayoutInflater = getLayoutInflater();
		AutoWrapLinearLayout tagContainer = (AutoWrapLinearLayout) findViewById(R.id.tag_container);
		for (String tag : list) {
			tagView = (TextView) mLayoutInflater.inflate(R.layout.tag, tagContainer, false);
			tagView.setText(tag);
			tagContainer.addView(tagView);
		}
	}
}
