package com.myandroid.main;

import java.util.ArrayList;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myandroid.main.R;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	public JSONArray  posts = null;	
	private static final String TAG_TITLE = "title";
	
	public MyAdapter(JSONArray  myposts) {
		posts = myposts;
	}

	@Override
	public int getCount() {
		return posts.length();
	}

	@Override
	public Object getItem(int index) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.time_list_item, parent, false);
		}

		JSONObject post ;
		String title =""; ;		
		
		try {
			// Getting JSON Array
			post = posts.getJSONObject(position);			
			title = post.getString(TAG_TITLE);	
			
			//databaseHelper.saveRecord(title, "");			
			
	      } catch (JSONException e) {
		      e.printStackTrace();
	      }  		
		
		
			
		TextView timeTextView = (TextView) convertView.findViewById(R.id.time_view);
		timeTextView.setText(title);		
		return convertView;
	}
	
}
