package com.activity.pulsealert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import com.rosselle.pulsealert.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Bluetooth  extends Activity implements OnClickListener{

	private static final int REQUEST_ENABLE_BT = 1;
	BluetoothAdapter btAdapter;
	Button jv_connect;
	BluetoothDevice myDevice;
	TextView jv_tvTesting;
	TextView jv_tvStart;
	ProgressDialog pDialog;
	IntentFilter filter;
	BroadcastReceiver mReceiver;

	BluetoothSocket mmsocket;
	Thread workerThread;
	byte[] readBuffer;
	int readBufferPosition;
	int counter;
	volatile boolean stopWorker;
	OutputStream mmOutputStream;
	InputStream mmInputStream;
	String bpm;
	Intent intentForHome;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth);	
		int condition = getIntent().getIntExtra("iAmOk", 0);
		if(condition == 1){
			checkIfThereIsBluetooth();
		}
		initialize();

	}

	private void initialize() {
		jv_connect = (Button) findViewById(R.id.btnConnect);
		jv_connect.setOnClickListener(this);
		jv_tvStart = (TextView) findViewById( R.id.tvStart);
		jv_tvTesting = (TextView) findViewById(R.id.tvTesting);

		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				//Aalamin kung may nakitang mga device

				if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
					final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
					if(state == BluetoothAdapter.STATE_ON){
						Toast.makeText(getApplicationContext(), "The Bluetooth is enabled", 0).show();
					}
				}else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
					jv_tvStart.setText("Nagstart");
					pDialog = new ProgressDialog(Bluetooth.this);
					pDialog.setCancelable(false);
					pDialog.setMessage("Looking for HC-05");
					pDialog.show();
				}else if(BluetoothDevice.ACTION_FOUND.equals(action)){
					Toast.makeText(getApplicationContext(), "FOUND", 0).show();
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					if(device.getName().equals("HC-05")){
						myDevice = device;
						jv_tvTesting.setText("Nakita "+ device.getName() + " : "+ device.getAddress());

						btAdapter.cancelDiscovery();
					}
				}else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
					Toast.makeText(getApplicationContext(), "FINISHED", 0).show();
					pDialog.dismiss();
					initializePairing();
<<<<<<< HEAD
					readDataFromBeatBox();
					/*Intent i = new Intent(Bluetooth.this, Home.class);
					i.putExtra("bpm",bpm);
					startActivity(i);*/
=======
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c
				}
			}
		};
		filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(mReceiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		registerReceiver(mReceiver, filter);
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnConnect:
			/*	Intent myIntent = new Intent(Bluetooth.this, Home.class);
			startActivity(myIntent);
			 */
			checkIfThereIsBluetooth();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			//Kung nabuksan na ung bluetooth, hanapin na yung HC-05 sa Paired Devices
			Set<BluetoothDevice> pairedDevices =btAdapter.getBondedDevices();
			if(pairedDevices.size() > 0){
				for(BluetoothDevice device : pairedDevices){
					if(device.getName().equals("HC-05")){
						myDevice = device;
						jv_tvTesting.setText(myDevice.getName());
						readDataFromBeatBox();
<<<<<<< HEAD

=======
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c
						break;
					}
				}
			}
			//Kapag walang laman ung paired, hanapin mo sa mga pakalat-kalat na device.
			if(myDevice == null){
				Toast.makeText(getApplicationContext(), "Discovery started for HC-05", 0).show();
				btAdapter.startDiscovery();

				break;
			}
		case RESULT_CANCELED:
			break;

		}
	}



	private void checkIfThereIsBluetooth() {
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if(btAdapter == null){
			AlertDialog.Builder alert = new AlertDialog.Builder(Bluetooth.this);
			alert.setTitle("Sorry!");
			alert
			.setMessage("Sorry, you don't have Bluetooth in your device!")
			.setCancelable(true);
			AlertDialog createMe = alert.create();
			createMe.show();
		}else{
			enableBluetooth();
		}
	}

	private void enableBluetooth() {
		//Kung hindi enabled, kailangan ienable ung bluetooth!
		if(!btAdapter.isEnabled()){
			Intent enableBIntent = new Intent (BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBIntent, REQUEST_ENABLE_BT);
		}else{
			Intent enableBIntent = new Intent (BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBIntent, REQUEST_ENABLE_BT);
		}
	}
	//////////////////////////////////////////////// PAIR NA DITO! ///////////////////////////////////////////////////////////////////////////////////////


	private void initializePairing() {
		if(myDevice.getBondState() == BluetoothDevice.BOND_BONDED){
			unpairDevice(myDevice);
		} else {
			pairDevice(myDevice);
<<<<<<< HEAD
			
		}
		registerReceiver(pairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
	}
=======
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c

		}
		/*registerReceiver(pairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));*/
	}
	private void unpairDevice(BluetoothDevice myDeviceForUnPair) {

		try {
			Method myMethod = myDeviceForUnPair.getClass().getMethod("removeBond", (Class[]) null);
			myMethod.invoke(myDeviceForUnPair, (Object[]) null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void pairDevice(BluetoothDevice myDeviceForPair) {
		try{
			Method myMethod = myDeviceForPair.getClass().getMethod("createBond", (Class[]) null);
			myMethod.invoke(myDeviceForPair, (Object[]) null);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private final BroadcastReceiver pairReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
				final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
				final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

				if(state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING){
					Toast.makeText(getApplicationContext(), "PAIRED", 0).show();
					//	readDataFromBeatBox();
				} else if(state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
					Toast.makeText(getApplicationContext(), "UNPAIRED", 0).show();
				}
			}

		}
	};
	/////////////////////////////////////////////////////// KUHA DATA! /////////////////////////////////////////////////////////////////////////////////////////////////

	private void readDataFromBeatBox() {
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Hindi na babaguhin to huh!
		try {
			mmsocket = myDevice.createRfcommSocketToServiceRecord(uuid);
			mmsocket.connect();
			mmOutputStream = mmsocket.getOutputStream();
			mmInputStream = mmsocket.getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final Handler handler = new Handler(); 
		final byte delimiter = 10; //This is the ASCII code for a newline character

		stopWorker = false;
		readBufferPosition = 0;
		readBuffer = new byte[1024];
		workerThread = new Thread(new Runnable()
		{
			public void run()
			{                
				while(!Thread.currentThread().isInterrupted() && !stopWorker)
				{
					try 
					{
						int bytesAvailable = mmInputStream.available();                        
						if(bytesAvailable > 0)
						{
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for(int i=0;i<bytesAvailable;i++)
							{
								byte b = packetBytes[i];
								if(b == delimiter)
								{
									byte[] encodedBytes = new byte[readBufferPosition];
									System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
									final String data = new String(encodedBytes, "US-ASCII");
									readBufferPosition = 0;

									handler.post(new Runnable()
									{
										public void run()
										{
<<<<<<< HEAD
											Intent i = new Intent(Bluetooth.this, Home.class);
											i.putExtra("bpm",data);
											startActivity(i);

											//	bpm = data;

=======
											bpm = data;
											new loadThePulseRate().execute(bpm);
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c

										}
									});
								}
								else
								{
									readBuffer[readBufferPosition++] = b;
								}
							}
						}
					} 
					catch (IOException ex) 
					{
						stopWorker = true;
					}
				}
			}
		});

		workerThread.start();
<<<<<<< HEAD
=======
	}

	/*@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}*/

	public class loadThePulseRate extends AsyncTask<String,Integer, Integer>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			intentForHome= new Intent(Bluetooth.this, Home.class);
			startActivity(intentForHome);
			
		}


		protected Integer doInBackground(String... params) {
		
			return null;
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}


>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c
	}

	/*@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}*/


}
