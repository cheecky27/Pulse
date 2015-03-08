package com.service.pulsealert;



import com.activity.pulsealert.Bluetooth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SrvBluetooth extends Service{
	private static final String TAG="com.service.pulsealert"; 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStart Method called");
		Runnable r = new Runnable(){
			public void run() {
			/*	Intent myBluetoothIntent = new Intent(SrvBluetooth.this, Bluetooth.class);
				startActivity(myBluetoothIntent); */
				
			}
		};
		Thread thread = new Thread(r);
		thread.start();
		return Service.START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy Method called");
	}

}
