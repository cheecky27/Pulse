package com.activity.pulsealert;

import com.rosselle.pulsealert.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent i = new Intent(SplashScreen.this, Login.class);
					startActivity(i);
					//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				}
			}

		};
		timer.start();
	}

	protected void onPause() {
		super.onPause();
		finish();
	}

}
