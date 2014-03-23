package com.zhoujanr.NewsTweets;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {

	private static final String TAG = "UpdateService";
	private static final int DELAY = 6000; //10 mins
	private boolean runFlag = false;
	private Updater updater;
	private YamaApplication yamba;
	private static final String NEW_STATUS_INTENT
						= "com.zhoujanr.NewsTweets.NEW_STATUS";
	private static final String NEW_STATUS_EXTRA_COUNT 
						= "new_status_extra_count";

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
		this.yamba = (YamaApplication)getApplication();
		
		Log.d(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		
		this.runFlag=true;
		this.updater.start();
		this.yamba.setServiceRunning(true);
		
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
		this.yamba.setServiceRunning(false);
		
		Log.d(TAG, "onDestroy");
	}

	/**
	 * Thread that performs the actual update from the online service
	 */
	private class Updater extends Thread{
		
		static final String RECEIVE_TIMELINE_NOTIFICATIONS =
		        "com.zhoujanr.NewsTweets.RECEIVE_TIMELINE_NOTIFICATIONS"; 

		
		Intent intent;
		
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
					YamaApplication yamba = (YamaApplication) updateService.getApplication();
					int newUpdate = yamba.fetchStatusUpdates();
					if(newUpdate > 0){
						Log.d(TAG, "We have a new status");
						intent = new Intent(NEW_STATUS_INTENT);
						intent.putExtra(NEW_STATUS_EXTRA_COUNT, newUpdate);
						updateService.sendBroadcast(intent, RECEIVE_TIMELINE_NOTIFICATIONS);
						
					}
					
					Thread.sleep(DELAY);
				}catch(InterruptedException e){
					updateService.runFlag = false;
				}
			}
		}
		
		
	}

}
