package com.activity.pulsealert;

import java.util.Random;

import com.db.pulsealert.MyDBHandler;
import com.model.pulsealert.User;
import com.rosselle.pulsealert.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SMSSender extends Activity{
	TextView tvView;
	TextView tvView1;
	TextView tvView2;
	Button btnSend1;	
	Button btnTesting;	
	Bundle bundleForReceiver;
	String number;
	String name;
	String num1;
	int iterate;
	ProgressDialog pDialog;
	IntentFilter intentFilter;
	String code, receiverNumber, receiverMessage;
	String wholeCode = "";
	boolean receivedOne, receivedTwo, receivedThree;
	Bundle data;
	private BroadcastReceiver intentReceiver = new BroadcastReceiver(){
		public void onReceive(Context context, Intent intent){
			receiverNumber = intent.getExtras().getString("sms_number");
			receiverMessage = intent.getExtras().getString("sms_message");

			if(receiverNumber.equals(number.split(",")[0])){
				tvView.setText("original: "+wholeCode.split(",")[0]+ " received: "+ receiverMessage);
				if(wholeCode.split(",")[0].equals(receiverMessage)){
					Toast.makeText(getApplication(), "OK  ZERO", Toast.LENGTH_LONG).show();
					receivedOne = true;
				}
			}
			if(receiverNumber.equals(number.split(",")[1])){
				tvView.setText("original: "+wholeCode.split(",")[1]+ " received: "+ receiverMessage);
				Toast.makeText(getApplication(), "TWO", Toast.LENGTH_LONG).show();
				if(wholeCode.split(",")[1].equals(receiverMessage)){
					Toast.makeText(getApplication(), "OK  ONE", Toast.LENGTH_LONG).show();
					receivedTwo = true;
				}
			}
			if(receiverNumber.equals(number.split(",")[2])){
				tvView.setText("original: "+wholeCode.split(",")[2]+ " received: "+ receiverMessage);
				Toast.makeText(getApplication(), "THREE", Toast.LENGTH_LONG).show();
				if(wholeCode.split(",")[2].equals(receiverMessage)){
					Toast.makeText(getApplication(), "OK  TWO", Toast.LENGTH_LONG).show();
					receivedThree = true;
				}
			}
			Toast.makeText(getApplication(), "one: "+ receivedOne+ " two: "+ receivedTwo+ " three: "+ receivedThree, 0).show();
			if(receivedOne && receivedTwo && receivedThree){
				Toast.makeText(getApplication(), "was here", 0).show();
				MyDBHandler myDBHandler = new MyDBHandler(SMSSender.this, null, null, 54);
				myDBHandler.addUser(data);
				User myUser = new User();
				myUser = myDBHandler.readData();
				tvView2.setText("Hi: " + myUser.get_name());
				Intent intentForBluetooth = new Intent("com.activity.pulsealert.BLUETOOTH");
				startActivity(intentForBluetooth);
			}
		}
	};
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smssender);

		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");

		tvView2 = (TextView)findViewById(R.id.tvView2);
		tvView1 = (TextView)findViewById(R.id.tvView1);
		tvView = (TextView)findViewById(R.id.tvView);
		btnSend1 = (Button) findViewById(R.id.btnSendMess1);
		btnTesting = (Button) findViewById(R.id.btnTesting);

		//Pass data to SMSReceiver
		Bundle sentData = getIntent().getExtras();
		data = sentData;
		//PART TO BE DELETED
		MyDBHandler myDBHandler = new MyDBHandler(SMSSender.this, null, null, 54);
		myDBHandler.addUser(data);
		
		
		number = sentData.getString("jv_famNum1");
		name = sentData.getString("jv_famName1");

		Intent intentToReceiver = new Intent("com.util.pulsealert.SMSRhhECEIVER");
		intentToReceiver.putExtras(sentData);



		btnSend1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pDialog = new ProgressDialog(SMSSender.this);
				pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pDialog.setCancelable(false);
				pDialog.setIndeterminate(false);
				pDialog.setMessage("Sending message");
				pDialog.setMax(3);
				pDialog.show();
				ProgressThread myProgressThread = new ProgressThread();
				myProgressThread.start();
			}
		});	

	}
	private void sendMessage(final String valNumber, final ProgressDialog pDialog) {
		String SENT = "SMS_SENT";
		String  DELIVERED = "SMS_DELIVERED";
		//Pending Intent para sa pag sesend
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		//eto naman Pending Intent para sa pag pagdedeliver la la la
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

		//sa send broadcast receiver toh
		BroadcastReceiver sendSMS = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				switch(getResultCode()){
				case Activity.RESULT_OK:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "SMS sent", Toast.LENGTH_SHORT).show();		
					break;
				case android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
					break;
				case android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "No Service", Toast.LENGTH_SHORT).show();
					break;
				case android.telephony.SmsManager.RESULT_ERROR_NULL_PDU:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
					break;
				case android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "Radio Off", Toast.LENGTH_SHORT).show();
					break;
				}

			}	
		};

		//Kailangan natin gumawa ng Broadcast Receiver para sa pag dedeliver!
		BroadcastReceiver deliverSMS = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				switch(getResultCode()){
				case Activity.RESULT_OK:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					pDialog.dismiss();
					Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		//na-send ba?
		registerReceiver(sendSMS, new IntentFilter(SENT));

		// na-deliver ba?
		registerReceiver(deliverSMS, new IntentFilter(DELIVERED));

		android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
		code = getCode();
		sms.sendTextMessage(valNumber, null, "Message from PulseAlert App: Please reply "
				+ "with the code provided to confirm that you have received this notification.  CODE: "
				+code, sentPI, deliveredPI);		
		wholeCode+= code+",";
	}

	private String getCode() {
		String code = "";
		Random r = new Random();
		String alphabet = "PulseAlert0027";
		for(int x=0; x<alphabet.length(); x++ ){
			code += alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return code;
	}
	protected void onResume(){
		registerReceiver(intentReceiver, intentFilter);
		super.onResume();
	}
	protected  void  onPause() {
		unregisterReceiver(intentReceiver);
		super.onPause();
	}

	private class ProgressThread extends Thread{
		public void run(){
			for(String valNumber : number.split(",")){
				try{
					iterate++;
					Thread.sleep(1000);		
					pDialog.setProgress(iterate);
					sendMessage(valNumber, pDialog);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
}
