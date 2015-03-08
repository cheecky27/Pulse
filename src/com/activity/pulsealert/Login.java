package com.activity.pulsealert;

import com.db.pulsealert.MyDBHandler;
import com.model.pulsealert.User;
import com.rosselle.pulsealert.R;
import com.service.pulsealert.SrvBluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{
	String username, password, usernameFromDb,passwordFromDb;
	Button jv_btnLogin;
	TextView jv_tvSignUp, jv_tvTesting, jv_tvTesting1;
	EditText jv_etUserName;
	EditText jv_etPassWord;
	boolean isUsernameRegistered = false;
	boolean isPasswordRegistered = false;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login1);
		initialize();

	}
	private void initialize() {
		jv_btnLogin = (Button) findViewById(R.id.btnSignIn);
		jv_etUserName = (EditText) findViewById(R.id.etUserName);
		jv_etPassWord = (EditText) findViewById(R.id.etLoginPass);
		jv_tvSignUp = (TextView) findViewById(R.id.tvSignup);
		jv_tvTesting = (TextView) findViewById(R.id.tvTesting);
		jv_tvTesting1 = (TextView) findViewById(R.id.tvTesting1);
		jv_tvSignUp.setOnClickListener(this);
		jv_btnLogin.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tvSignup:
			Intent i = new Intent("com.rosselle.pulsealert.SIGNUP");
			startActivity(i);
			/*Intent intentForBluetooth = new Intent("com.activity.pulsealert.BLUETOOTH");
			startActivity(intentForBluetooth);*/
			break;
		case R.id.btnSignIn:
			MyDBHandler myDBHandler = new MyDBHandler(Login.this, null, null, 54);
			User myUser = myDBHandler.getTheNames();
			jv_tvTesting.setText("Username: "+myUser.get_userName());
			//jv_tvTesting1.setText("Password: "+myUser.get_password());
			username=jv_etUserName.getText().toString();
			password=jv_etPassWord.getText().toString();
			usernameFromDb=myUser.get_userName();
			passwordFromDb=myUser.get_password();
			for(int x=0; x<usernameFromDb.split(",").length; x++){
				if(username.equals(usernameFromDb.split(",")[x])){

					isUsernameRegistered = true;
				}
			}

			for(int x=0; x<passwordFromDb.split(",").length; x++){
				if(password.equals(passwordFromDb.split(",")[x])){
					isPasswordRegistered = true;
				}
			}

			Toast.makeText(getApplication(), "user registered: "+ isUsernameRegistered, 0).show();
			Toast.makeText(getApplication(), "pass registered: "+ isPasswordRegistered, 0).show();
			if(isUsernameRegistered && isPasswordRegistered){
				isUsernameRegistered = false;
				isPasswordRegistered = false;
				Intent myIntent = new Intent(Login.this, Bluetooth.class);
				startActivity(myIntent);
				
				/*Intent myIntent = new Intent(Login.this, SrvBluetooth.class);
				startService(myIntent);*/
			}
			break;
		}


	}


}
