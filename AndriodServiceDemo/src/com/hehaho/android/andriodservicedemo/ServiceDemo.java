/**
 * 
 */
package com.hehaho.android.andriodservicedemo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * @author kevin
 *
 */
public class ServiceDemo extends Service {

	private Timer timer;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		timer = new Timer();
		
		Log.i("AndroidServiceDemo", "Service demo is created!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
//		return super.onStartCommand(intent, flags, startId);
		Log.i("AndroidServiceDemo", "intent = " + intent);
		startBackgroundTask(intent, startId);
		return Service.START_STICKY;
	}

	public class ServiceDemoBinder extends Binder {
		ServiceDemo getService(){
			return ServiceDemo.this;
		}
	}
	
	private final IBinder binder = new ServiceDemoBinder();
	
	private void startBackgroundTask(Intent intent, int startId){
//		Thread service = new Thread(null, backendServiceThread, "ServiceDemo thread");
//		service.start();
		
		timer.schedule(new TimerTask(){
			public void run(){
				Log.i("AndroidServiceDemo", "It's running, on timer task!");
			}
		}, 0, 1000);
	}
	
	private Runnable backendServiceThread = new Runnable() {
		public void run(){
			backendServiceProcessing();
		}
	};
	
	private void backendServiceProcessing(){
		Log.i("AndroidServiceDemo", "Service demo is runnings!");
		int count = 5;
		int i =0;
		while(true && i <= count){
			Log.i("AndroidServiceDemo", "Printing then sleep 1s!");
			try {
				Thread.sleep(1000);
				i++;
			} catch (InterruptedException e) {
				Log.e("AndroidServiceDemo", "Sleep 1s failed");
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		backendServiceThread = null;
		timer.cancel();
		Log.i("AndroidServiceDemo", "onDestroy... set backend Service thread to null.");	
	}
	
	
}
