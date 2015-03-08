package com.broadcastreceiver.pulsealert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		SmsMessage[]  messages = null;
		String str = "";
		if(bundle != null){
			Object [] pdus = (Object[]) bundle.get("pdus");
			messages = new SmsMessage[pdus.length];
			for(int i=0; i<messages.length; i++){
				messages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
				str += "Message from "+ messages[i].getOriginatingAddress();
				str += " :";
				str += messages[i].getMessageBody().toString();
				str += "\n";

				String number = messages[i].getOriginatingAddress();
				String message = messages[i].getMessageBody().toString();
				//Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
				Intent broadcastIntent = new Intent();
				broadcastIntent.setAction("SMS_RECEIVED_ACTION");
				broadcastIntent.putExtra("sms_number", number);
				broadcastIntent.putExtra("sms_message", message);
				context.sendBroadcast(broadcastIntent);
				
				//if(messages[i].getOriginatingAddress().equals(other))
			}
		}
		
	}

}
