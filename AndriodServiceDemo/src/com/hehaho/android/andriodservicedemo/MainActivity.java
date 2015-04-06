package com.hehaho.android.andriodservicedemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button startBtn;
	
	private Button stopBtn;
	
	private ServiceDemo serviceInstance;
	
	private TextView textStatus;
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			Log.i("AndroidServiceDemo", "ServiceDemo is connected!");
			textStatus.setText("Service is connected!");
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.i("AndroidServiceDemo", "ServiceDemo is disconnected!");
			textStatus.setText("Service is disconnected!");
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startBtn = (Button)this.findViewById(R.id.startBtn);
		stopBtn = (Button)this.findViewById(R.id.stopBtn);
		
		textStatus = (TextView)this.findViewById(R.id.textStatus);
		
		startService();
		
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("AndroidServiceDemo", "Try to start the service...");
				startService();
			}
		});
		
		stopBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("AndroidServiceDemo", "Try to stop the service...");
				stopService();
			}
		});
		
		Intent bindIntent = new Intent(MainActivity.this, ServiceDemo.class);
		bindService(bindIntent, mConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void startService(){
		Intent intent = new Intent(this, ServiceDemo.class);
		startService(intent);
	}
	
	private void stopService(){
		stopService(new Intent(MainActivity.this, ServiceDemo.class));
		Log.i("AndroidServiceDemo", "Finshed calling stop service...");
	}
}
