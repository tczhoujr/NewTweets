package com.zhoujanr.NewsTweets;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {

	private static final String TAG = "UpdateService";
	private static final int DELAY = 600000; //10 mins
	private boolean runFlag = false;
	private Updater updater;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.updater = new Updater();
		Log.d(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		
		this.runFlag=true;
		this.updater.start();
		
		Log.d(TAG, "onStart");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		this.runFlag = false;
		this.updater.interrupt();
		this.updater = null;
		
		Log.d(TAG, "onDestroy");
	}

	/**
	 * Thread that performs the actual update from the online service
	 */
	private class Updater extends Thread{
		
		public Updater(){
			super("UpdaterService-Updater");
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			UpdateService updateService = UpdateService.this;
			while(updateService.runFlag){
				Log.d(TAG, "Updater running");
				try{
					//some work goes here...
					Log.d(TAG, "Updater ran");
					Thread.sleep(DELAY);
				}catch(InterruptedException e){
					updateService.runFlag = false;
				}
			}
		}
		
		
	}

}
