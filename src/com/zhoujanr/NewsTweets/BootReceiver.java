package com.zhoujanr.NewsTweets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		context.startService(new Intent(context, UpdateService.class));
		Log.d("BootRecevier","onReceived");
	}
	
}
