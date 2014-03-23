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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TimelineActivity extends Activity{
	
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private ListView listTimeline;
	private SimpleCursorAdapter adapter;
	
	static final String[] FROM = { DbHelper.C_CREATED_AT, DbHelper.C_USER, DbHelper.C_TEXT };
	static final int[] TO = { R.id.textCreatedAt, R.id.textUser, R.id.textText };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		listTimeline = (ListView) findViewById(R.id.listTimeline);
		
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
		//get the data from database
		cursor = db.query(DbHelper.TABLE, null, null, null, null, null, DbHelper.C_CREATED_AT + " DESC");
		startManagingCursor(cursor);
		
		// Setup the adapter
	    adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);  // http://dev.icybear.net/learning-android-cn/images/7.png
	    listTimeline.setAdapter(adapter); 

		
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
