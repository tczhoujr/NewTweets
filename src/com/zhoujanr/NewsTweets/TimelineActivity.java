package com.zhoujanr.NewsTweets;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

}
