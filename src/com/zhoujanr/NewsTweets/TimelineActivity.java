package com.zhoujanr.NewsTweets;

import com.zhoujanr.NewsTweets.StatusActivity.PostToTwitter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TimelineActivity extends Activity{
	
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private TextView textTimeline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		textTimeline = (TextView) findViewById(R.id.textTimeline);
		
		//connect to database
		dbHelper = new DbHelper(this);
		db = dbHelper.getReadableDatabase();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		//close database
		db.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		textTimeline.setText(null);
		//get the data from database
		cursor = db.query(DbHelper.TABLE, null, null, null, null, null, DbHelper.C_CREATED_AT + " DESC");
		startManagingCursor(cursor);
		
		//Iterate over all the data and print it out
		String user, text, output;
		while(cursor.moveToNext()){
			user = cursor.getString(cursor.getColumnIndex(DbHelper.C_USER));
			text = cursor.getString(cursor.getColumnIndex(DbHelper.C_TEXT));
			output = String.format("%s:%s\n", user, text);
			textTimeline.append(output);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline_activity_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.itemPrefs:
				startActivity(new Intent(this, PrefsActivity.class));
				break;
			case R.id.itemServiceStart:
				startService(new Intent(this, UpdateService.class));
				break;
			case R.id.itemServiceStop:
				stopService(new Intent(this, UpdateService.class));
				break;
			case R.id.itemNewTweet:
				startActivity(new Intent(this, StatusActivity.class));
		}
		
		return true;
	}

}
