package com.zhoujanr.NewsTweets;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;


public class YamaApplication extends Application implements OnSharedPreferenceChangeListener {
	
	private static final String TAG = YamaApplication.class.getSimpleName();
	public Twitter twitter;
	private SharedPreferences prefs;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		Log.i(TAG,"onCreate");
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.i(TAG, "onTerminate");
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		this.twitter=null;
	}
	
	public synchronized Twitter getTwitter(){
		
		if(this.twitter==null){
			String username = this.prefs.getString("username", "");
			String password = this.prefs.getString("password", "");
			String apiRoot = this.prefs.getString("apiRoot","http://yamba.marakana.com/api");
			if(!TextUtils.isEmpty(username)&& !TextUtils.isEmpty(password) 
					&& !TextUtils.isEmpty(apiRoot)){
				this.twitter = new Twitter(username, password);
				twitter.setAPIRootUrl(apiRoot);
			}
		}
		return twitter;
	}

}
