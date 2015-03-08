package com.activity.pulsealert;

import com.db.pulsealert.MyDBHandler;
import com.model.pulsealert.User;
import com.rosselle.pulsealert.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
<<<<<<< HEAD
=======
import android.os.AsyncTask;
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
<<<<<<< HEAD
import android.view.ViewGroup;
import android.widget.TextView;
=======
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c
import android.os.Vibrator;

public class Home extends Activity{

	TextView tv_pulse;
	TextView tv_name;
	TextView tv_number;
	Vibrator vibrator;
	MediaPlayer mMediaPlayer;
	AudioManager audioManager;
	PowerManager powerManager;
	WakeLock wakeLock;
	int bpm;
	double pulse;
	AlertDialog createTheDialog;
	AlertDialog.Builder builder;
	View vg;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);	
		initialize();
<<<<<<< HEAD
		pulse = Double.parseDouble(getIntent().getExtras().getString("bpm"));
		bpm = (int) pulse;

		//Log.i("pulse", "Pulse Rate: " + bpm);
		
		tv_pulse.setText(Integer.toString(bpm));
		feelThePulse();


		/*	MyDBHandler myDBHandler = new MyDBHandler(Home.this, null, null, 53);
		String names = myDBHandler.getTheNames();
		name.setText(names);*/
	}
	private void initialize() {

		tv_pulse = (TextView) findViewById(R.id.tvPulse);
		tv_name = (TextView) findViewById(R.id.tvHomeName);
		tv_number = (TextView) findViewById(R.id.tvHomeNumber);
		powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "awake");
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mMediaPlayer =MediaPlayer.create(this, R.raw.bomp);
		builder = new AlertDialog.Builder(Home.this);
		builder.setMessage("Are you okay?").setPositiveButton("YES", dialogClicklistener)
		.setNegativeButton("NO", dialogClicklistener);	
		vg = findViewById(R.id.home);



	}
	private void feelThePulse() {

		if(bpm >= 60 && bpm <= 100){
			stopIt();		
		}else if(bpm < 60 || bpm > 100) {
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();
			}else{
				vibrateThePhone();
				ringThePhone();
				showDialog();

			}
		}
	}
=======

	}

	private void initialize() {
		tv_pulse = (TextView) findViewById(R.id.tvPulse);
		tv_name = (TextView) findViewById(R.id.tvHomeName);
		tv_number = (TextView) findViewById(R.id.tvHomeNumber);
		powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "awake");
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mMediaPlayer =MediaPlayer.create(this, R.raw.bomp);
		builder = new AlertDialog.Builder(Home.this);
		builder.setMessage("Are you okay?").setPositiveButton("YES", dialogClicklistener)
		.setNegativeButton("NO", dialogClicklistener);	
		vg = findViewById(R.id.home);
	}

	public void doReceivePulseRate(String pulse){
		new loadPulseRate().execute(pulse);
	}

	public class loadPulseRate extends AsyncTask<String, Integer, Integer>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			setContentView(R.layout.home);	
			initialize();
			//tv_pulse =(TextView) findViewById(R.id.tvPulse);

		}

		@Override
		protected Integer doInBackground(String... params) {
			for(int x=0; x<params.length; x++){
				Log.i("pulse", "The pulse rate: "+ params[x]);
				pulse = Double.parseDouble(params[x]);
				bpm = (int)pulse;

			}
			return bpm;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.i("pusoooo", "The result is: "+ result);
			tv_pulse.setText(result);
		}


	}

	private void feelThePulse() {

		if(bpm >= 60 && bpm <= 100){
			stopIt();		
		}else if(bpm < 60 || bpm > 100) {
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();
			}else{
				vibrateThePhone();
				ringThePhone();
				showDialog();

			}
		}
	}
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c
	private void wakeLock() {
		wakeLock.acquire();
	}

	private void ringThePhone() {
		try{
			mMediaPlayer.setLooping(true);
			mMediaPlayer.start();
			audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void vibrateThePhone() {
		long[] pattern = {0, 500, 1000};
		vibrator.vibrate(pattern, 0);
	}

	private void stopIt(){
		mMediaPlayer.setLooping(false);
		mMediaPlayer.stop();
		mMediaPlayer.release();	
		vibrator.cancel();	
	}
	private void showDialog(){
		createTheDialog = builder.create();
		createTheDialog.show();

	}
	DialogInterface.OnClickListener dialogClicklistener = new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:

				Thread timer = new Thread(){
					public void run(){
						try{
							createTheDialog.dismiss();
							stopIt();
							sleep(60000);
						}catch(InterruptedException e){
							e.printStackTrace();
<<<<<<< HEAD
						}finally{
=======
						}
						finally{
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c

							Intent myIntent = new Intent(Home.this, Bluetooth.class);
							myIntent.putExtra("iAmOk", 1);
							startActivity(myIntent);	
							//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						}
					}

				};
				timer.start();




				break;
			case DialogInterface.BUTTON_NEGATIVE:
				MyDBHandler myDBHandler = new MyDBHandler(Home.this, null, null, 54);
				User myUser = myDBHandler.readData();
				tv_name.setText(myUser.get_name());
				tv_number.setText(myUser.get_famContactNumber1());
				break;
			}

		}
<<<<<<< HEAD

	};
=======
>>>>>>> 34ba1af448247ca92638de36a5c335bdf0fdd53c

	};
}
