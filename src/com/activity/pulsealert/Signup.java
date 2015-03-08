package com.activity.pulsealert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.rosselle.pulsealert.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView.OnEditorActionListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Signup extends Activity implements OnClickListener, OnCheckedChangeListener, OnEditorActionListener{

	EditText jv_name, jv_password ,jv_userName, jv_Bday, jv_age;
	TextView jv_tvValidateName, jv_tvValidateUserName, jv_tvValidatePassword, jv_tvValidateBday,jv_tvValidateAge, jv_tvValidatefam, jv_tvValidateDoc;
	RadioGroup jv_rdgrpGender;
	EditText jv_famName1, jv_famName2, jv_famName3;
	EditText jv_famNum1, jv_famNum2, jv_famNum3;
	EditText jv_docName, jv_docNum;
	Button jv_btnSignUp;
	String gender;
	boolean isValidated;
	boolean isThereANumOrSpace;
	Calendar calBday = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener dateBday = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			calBday.set(Calendar.YEAR, year);
			calBday.set(Calendar.MONTH, monthOfYear);
			calBday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			String myFormat = "MM/dd/yy"; //In which you need put here
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

			jv_Bday.setText(sdf.format(calBday.getTime()));
			if(calBday.getTime().getYear() == (calBday.getInstance().getTime().getYear() - (Integer.parseInt(jv_age.getText().toString())+1)  )){
				jv_tvValidateBday.setText("Birthday Validated!");
				jv_tvValidateBday.setTextColor(getResources().getColor(R.color.greenValidation));
			} else {
				jv_tvValidateBday.setText("Your age birthday does not match the age you provided!");
				jv_tvValidateBday.setTextColor(getResources().getColor(R.color.red));
			}
		}
	};

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		initialize();
		doNotEnable();
	}

	private void initialize() {
		jv_name = (EditText) findViewById(R.id.etName);
		jv_password = (EditText) findViewById( R.id.etSignupPass);
		jv_userName = (EditText) findViewById(R.id.etUserName);
		jv_Bday = (EditText) findViewById(R.id.etBday);
		jv_age = (EditText) findViewById(R.id.etAge);
		jv_rdgrpGender = (RadioGroup) findViewById(R.id.rdgrpGender);
		jv_famName1 = (EditText) findViewById(R.id.etFamName1);
		jv_famName2 = (EditText) findViewById(R.id.etFamName2);
		jv_famName3 = (EditText) findViewById(R.id.etFamName3);
		jv_famNum1 = (EditText) findViewById(R.id.etFamNum1);
		jv_famNum2 = (EditText) findViewById(R.id.etFamNum2);
		jv_famNum3 = (EditText) findViewById(R.id.etFamNum3);
		jv_docName = (EditText) findViewById(R.id.etDocName);
		jv_docNum = (EditText) findViewById(R.id.etDocNum);
		jv_btnSignUp = (Button) findViewById(R.id.btnSignUp);
		//Validations:
		jv_tvValidateName = (TextView) findViewById(R.id.tvValidateName);
		jv_tvValidateUserName = (TextView) findViewById(R.id.tvValidateUserName);
		jv_tvValidatePassword = (TextView) findViewById(R.id.tvValidatePassword);
		jv_tvValidateAge = (TextView) findViewById(R.id.tvValidateAge);
		jv_tvValidateBday = (TextView) findViewById(R.id.tvValidateBday);
		jv_tvValidatefam = (TextView) findViewById(R.id.tvValidateFam);
		jv_tvValidateDoc = (TextView) findViewById(R.id.tvValidateDoc);
		//Listeners
		jv_rdgrpGender.setOnCheckedChangeListener(this);
		jv_btnSignUp.setOnClickListener(this);
		jv_name.setOnEditorActionListener(this);
		jv_userName.setOnEditorActionListener(this);
		jv_password.setOnEditorActionListener(this);
		jv_age.setOnEditorActionListener(this);
		jv_Bday.setOnEditorActionListener(this);
		jv_Bday.setOnClickListener(this);
		jv_famName1.setOnEditorActionListener(this);
		jv_famName2.setOnEditorActionListener(this);
		jv_famName3.setOnEditorActionListener(this);
		jv_famNum1.setOnEditorActionListener(this);
		jv_famNum2.setOnEditorActionListener(this);
		jv_famNum3.setOnEditorActionListener(this);
		jv_docName.setOnEditorActionListener(this);
		jv_docNum.setOnEditorActionListener(this);
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.rdMale :
			gender = "Male";
			jv_famName1.setEnabled(true);
			jv_famName1.requestFocus();
			break;
		case R.id.rdFemale :
			gender = "Female";
			jv_famName1.setEnabled(true);
			jv_famName1.requestFocus();
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnSignUp:
			Intent intentForFamilyConfirmation = new Intent("com.rosselle.pulsealert.SMSSENDER");
			intentForFamilyConfirmation.putExtras(sendData());
			startActivity(intentForFamilyConfirmation);
			/*Intent intentForHome = new Intent("com.activity.pulsealert.HOME");
			startActivity(intentForHome);*/
			break;
		case R.id.etBday:
			showTheDatePicker();
			break;
		case R.id.etFamName1:
			if( Integer.parseInt(jv_famName1.getText().toString()) > 8) {
				jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
				jv_tvValidatefam.setText("Family Name Validated!");
				jv_famNum1.setEnabled(true);
				jv_famNum1.requestFocus();
			}else{
				jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
				jv_tvValidatefam.setText("Please enter the name of the person to be informed in case of emergency!");
				jv_famName1.setText("");
				jv_famName1.setFocusable(true);
			}
			break;
		}
	}

	public boolean checkIfNotALetter(EditText editText){
		for(int x=0; x<editText.getText().length(); x++){
			if(Character.isDigit(editText.getText().toString().charAt(x)) || editText.getText().toString().startsWith(" ")){
				isThereANumOrSpace = false;
				break;
			}else{
				isThereANumOrSpace = true;
			}
		}
		return isThereANumOrSpace;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if(v.getId() == R.id.etName && jv_name.hasFocus()){
			doNotEnable();

			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if(checkIfNotALetter(jv_name) && jv_name.getText().length() > 6) {
					jv_tvValidateName.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidateName.setText("Name Validated!");
					jv_userName.setEnabled(true);
					jv_userName.requestFocus();
				}else{
					jv_tvValidateName.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidateName.setText("Please enter your real name!");
					jv_name.setText("");
					jv_name.setFocusable(true);

				}
				return true;
			}
		}
		//Username	
		if(v.getId() == R.id.etUserName && jv_userName.hasFocus()){

			jv_userName.setEnabled(true);
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( jv_userName.getText().length() > 6) {
					jv_tvValidateUserName.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidateUserName.setText("username Validated!");
					jv_password.setEnabled(true);	
					jv_password.requestFocus();
				}else{
					jv_tvValidateUserName.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidateUserName.setText("Please enter a good username!!");
					jv_userName.setText("");
					jv_userName.setFocusable(true);

				}
				return true;
			}
		}
		//password
		if(v.getId() == R.id.etSignupPass && jv_password.hasFocus()){

			jv_password.setEnabled(true);
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( jv_password.getText().length() > 6) {
					jv_tvValidatePassword.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatePassword.setText("password Validated!");
					jv_age.setEnabled(true);		
					jv_age.requestFocus();
				}else{
					jv_tvValidatePassword.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatePassword.setText("Please enter a good password!!");
					jv_password.setText("");
					jv_password.setFocusable(true);

				}
				return true;
			}
		}

		if(v.getId() == R.id.etAge && jv_age.hasFocus()){

			jv_age.setEnabled(true);
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( Integer.parseInt(jv_age.getText().toString()) > 8) {
					jv_tvValidateAge.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidateAge.setText("Age Validated!");
					jv_Bday.setEnabled(true);
					jv_Bday.requestFocus();
					showTheDatePicker();

				}else{
					jv_tvValidateAge.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidateAge.setText("You should be 8yrs. old and above!!");
					jv_age.setText("");
					jv_age.setFocusable(true);

				}
				return true;
			}
		}

		if(v.getId() == R.id.etFamName1 && jv_famName1.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if(checkIfNotALetter(jv_famName1) && jv_famName1.getText().length() > 6) {
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatefam.setText("Family Name Validated!");
					jv_famNum1.setEnabled(true);
					jv_famNum1.requestFocus();
				}else{
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatefam.setText("Please enter the name of the person to be informed in case of emergency!");
					jv_famName1.setText("");
					jv_famName1.setFocusable(true);

				}
				return true;
			}
		}

		if(v.getId() == R.id.etFamNum1 && jv_famNum1.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( (jv_famNum1.getText().length()) == 13) {
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatefam.setText("Family Number Validated!");
					jv_famName2.setEnabled(true);
					jv_famName2.requestFocus();


				}else{
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatefam.setText("Please enter an active and correct cellphone number");
					jv_famNum1.setText("");
					jv_famNum1.setFocusable(true);

				}
				return true;
			}
		}
		if(v.getId() == R.id.etFamName2 && jv_famName2.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if(checkIfNotALetter(jv_famName2) && jv_famName2.getText().length() > 6) {
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatefam.setText("Family Name Validated!");
					jv_famNum2.setEnabled(true);
					jv_famNum2.requestFocus();


				}else{
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatefam.setText("Please enter the name of the person to be informed in case of emergency!");
					jv_famName2.setText("");
					jv_famName2.setFocusable(true);
				}
				return true;
			}
		}
		if(v.getId() == R.id.etFamNum2 && jv_famNum2.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( (jv_famNum2.getText().length()) == 13) {
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatefam.setText("Family Number Validated!");
					jv_famName3.setEnabled(true);
					jv_famName3.requestFocus();


				}else{
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatefam.setText("Please enter an active and correct cellphone number");
					jv_famNum2.setText("");
					jv_famNum2.setFocusable(true);

				}
				return true;
			}
		}

		if(v.getId() == R.id.etFamName3 && jv_famName3.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if(checkIfNotALetter(jv_famName3) && jv_famName3.getText().length() > 6) {
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatefam.setText("Family Name Validated!");
					jv_famNum3.setEnabled(true);
					jv_famNum3.requestFocus();


				}else{
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatefam.setText("Please enter the name of the person to be informed in case of emergency!");
					jv_famName3.setText("");
					jv_famName3.setFocusable(true);
				}
				return true;
			}
		}

		if(v.getId() == R.id.etFamNum3 && jv_famNum3.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( (jv_famNum3.getText().length()) == 13) {
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidatefam.setText("Family Number Validated!");
					jv_docName.setEnabled(true);
					jv_docName.requestFocus();


				}else{
					jv_tvValidatefam.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidatefam.setText("Please enter an active and correct cellphone number");
					jv_famNum3.setText("");
					jv_famNum3.setFocusable(true);

				}
				return true;
			}
		}

		if(v.getId() == R.id.etDocName && jv_docName.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if(checkIfNotALetter(jv_docName) && jv_docName.getText().length() > 6) {
					jv_tvValidateDoc.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidateDoc.setText("Doctor Name Validated!");
					jv_docNum.setEnabled(true);
					jv_docNum.requestFocus();


				}else{
					jv_tvValidateDoc.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidateDoc.setText("Please enter the name of the person to be informed in case of emergency!");
					jv_docName.setText("");
					jv_docName.setFocusable(true);
				}
				return true;
			}
		}

		if(v.getId() == R.id.etDocNum && jv_docNum.hasFocus()){
			if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
				if( (jv_docNum.getText().length()) == 1) {
					jv_tvValidateDoc.setTextColor(getResources().getColor(R.color.greenValidation));
					jv_tvValidateDoc.setText("Family Number Validated!");
					jv_btnSignUp.setEnabled(true);
				}else{
					jv_tvValidateDoc.setTextColor(getResources().getColor(R.color.red));
					jv_tvValidateDoc.setText("Please enter an active and correct cellphone number");
					jv_docNum.setText("");
					jv_docNum.setFocusable(true);

				}
				return true;
			}
		}
		return false;

	}


	public void showTheDatePicker(){
		new DatePickerDialog(Signup.this, dateBday, calBday
				.get(Calendar.YEAR), calBday.get(Calendar.MONTH),
				calBday.get(Calendar.DAY_OF_MONTH)).show();
	}

	public Bundle sendData() {
		Bundle bundle = new Bundle();

		bundle.putString("jv_name",jv_name.getText().toString());
		bundle.putString("jv_userName",jv_userName.getText().toString());
		bundle.putString("jv_password",jv_password.getText().toString());	
		bundle.putInt("jv_age",Integer.parseInt(jv_age.getText().toString()));	
		bundle.putString("jv_Bday",jv_Bday.getText().toString());
		bundle.putString("jv_gender",gender);
		bundle.putString("jv_famName1",jv_famName1.getText().toString() +","+ jv_famName2.getText().toString() + "," +jv_famName3.getText().toString());
		bundle.putString("jv_famNum1",jv_famNum1.getText().toString() +","+ jv_famNum2.getText().toString() +","+ jv_famNum3.getText().toString());		
		bundle.putString("jv_docName",jv_docName.getText().toString());
		bundle.putString("jv_docNum",jv_docNum.getText().toString());

		return bundle;
	}

	public void doNotEnable(){
		jv_userName.setEnabled(false);
		jv_password.setEnabled(false);
		jv_age.setEnabled(false);
		jv_Bday.setEnabled(false);
		jv_famName1.setEnabled(false);
		jv_famName2.setEnabled(false);
		jv_famName3.setEnabled(false);
		jv_famNum1.setEnabled(false);
		jv_famNum2.setEnabled(false);
		jv_famNum3.setEnabled(false);
		jv_docName.setEnabled(false);
		jv_docNum.setEnabled(false);
		jv_btnSignUp.setEnabled(false);
	}
}
