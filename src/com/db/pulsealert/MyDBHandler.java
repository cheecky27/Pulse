package com.db.pulsealert;

import com.model.pulsealert.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper{


	private static final int DATABASE_VERSION = 55;//Need to change this everytime you update the db
	private static final String DATABASE_NAME = "users.db";
	private static final String TABLE_USER = "users";

	private static final String  COLUMN_ID = "_id";
	private static final String  COLUMN_NAME = "name";
	private static final String  COLUMN_USERNAME = "username";
	private static final String  COLUMN_PASSWORD = "password";
	private static final String  COLUMN_AGE = "age";	
	private static final String  COLUMN_BDAY = "bday";
	private static final String  COLUMN_GENDER = "gender";
	private static final String  COLUMN_FAMILY_NAME = "family_name";
	private static final String  COLUMN_FAMILY_CONTACT_NUMBER = "family_contact_number";
	private static final String  COLUMN_DOCTOR = "doctor";
	private static final String  COLUMN_DOCTOR_CONTACT_NUMBER = "doctor_contact_number";

	public MyDBHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);	
	}

	public void onCreate(SQLiteDatabase db) {
		String query = "CREATE TABLE" +  "\'" + TABLE_USER +"\'"   + "(" +
				"\'"+COLUMN_ID+"\'"+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"\'"+COLUMN_NAME+"\'" + "TEXT," +
				"\'"+COLUMN_USERNAME+"\'"+ "TEXT," +
				"\'"+COLUMN_PASSWORD +"\'"+ "TEXT," +
				"\'"+COLUMN_AGE+"\'" + "INT," +
				"\'"+COLUMN_BDAY+"\'" + "TEXT," +
				"\'"+COLUMN_GENDER+"\'" + "TEXT," +
				"\'"+COLUMN_FAMILY_NAME+"\'" +"TEXT," +
				"\'"+COLUMN_FAMILY_CONTACT_NUMBER+"\'" +"TEXT," +
				"\'"+COLUMN_DOCTOR + "\'"+"TEXT," +
				"\'"+COLUMN_DOCTOR_CONTACT_NUMBER + "\'"+"TEXT" +
				" ); ";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + "\'" + TABLE_USER +"\';");
		onCreate(db);

	}

	//add a new row to the database

	public void addUser(Bundle user){
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, user.getString("jv_name"));
		values.put(COLUMN_USERNAME, user.getString("jv_userName"));
		values.put(COLUMN_PASSWORD, user.getString("jv_password"));
		values.put(COLUMN_AGE, user.getInt("jv_age"));
		values.put(COLUMN_BDAY, user.getString("jv_Bday"));
		values.put(COLUMN_GENDER, user.getString("jv_gender"));
		values.put(COLUMN_FAMILY_NAME, user.getString("jv_famName1"));
		values.put(COLUMN_FAMILY_CONTACT_NUMBER,user.getString("jv_famNum1"));
		values.put(COLUMN_DOCTOR, user.getString("jv_docName"));
		values.put(COLUMN_DOCTOR_CONTACT_NUMBER, user.getString("jv_docNum"));
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TABLE_USER, null, values);
		db.close();
	}

	//delete user from the database
	public void deleteUser(String userName){
		SQLiteDatabase db = getWritableDatabase();
		//db.execSQL("DELETE FROM " + "\'"+TABLE_USER+"\'" + " WHERE " + "\'"+COLUMN_AGE+"\'" + " = " + "\'"+userName+"\';");
		//db.delete(TABLE_USER,  "\'"+COLUMN_AGE+"\'" + "=" + "\'"+userName+"\'", null);
		db.delete(TABLE_USER, "age = ?", new String[] { userName });

	}

	//Print out the database as a String
	public User  readData(){
		User myUser = new User();
		String dbString = "";
		SQLiteDatabase db = getWritableDatabase();
		//String query = "SELECT * FROM" + "\'" + TABLE_USER +"\'" 
		//+" ORDER BY "+"\'id\' ASC LIMIT 1" +";";
		String query = "SELECT * FROM" + "\'" + TABLE_USER +"\'"+";";
		//cursor point to  a location in your results
		Cursor c = db.rawQuery(query, null);
		//move to the first row in your results
		c.moveToFirst();
		//	dbString = c.getString(c.getColumnIndex("name"));
		while(!c.isAfterLast()){
			if(c.getString(c.getColumnIndex("name")) != null){
				myUser.set_name(c.getString(c.getColumnIndex("name")));		
			}
			if(c.getString(c.getColumnIndex("username")) != null){
				myUser.set_userName(c.getString(c.getColumnIndex("username")));		
			}
			if(c.getString(c.getColumnIndex("password")) != null){
				myUser.set_password(c.getString(c.getColumnIndex("password")));		
			}
			if(c.getString(c.getColumnIndex("age")) != null){
				myUser.set_age(c.getInt(c.getColumnIndex("age")));		
			}
			if(c.getString(c.getColumnIndex("bday")) != null){
				myUser.set_bday(c.getString(c.getColumnIndex("bday")));		
			}

			if(c.getString(c.getColumnIndex("gender")) != null){
				myUser.set_gender(c.getString(c.getColumnIndex("gender")));			
			}			
			if(c.getString(c.getColumnIndex("family_name")) != null){
				myUser.set_familyName1(c.getString(c.getColumnIndex("family_name")));		
			}
			if(c.getString(c.getColumnIndex("family_contact_number")) != null){
				myUser.set_famContactNumber1(c.getString(c.getColumnIndex("family_contact_number")));			
			}
			if(c.getString(c.getColumnIndex("doctor")) != null){
				myUser.set_doctor(c.getString(c.getColumnIndex("doctor")));			
			}
			if(c.getString(c.getColumnIndex("doctor_contact_number")) != null){
				myUser.set_doctorContactNumber(c.getString(c.getColumnIndex("doctor_contact_number")));			
			}
			c.moveToNext();
		}
		db.close();
		return myUser;
	}

	public User  getTheNames(){
		User myUser = new User();
		String username = "";
		String password = "";
		SQLiteDatabase db = getWritableDatabase();
		//String query = "SELECT * FROM" + "\'" + TABLE_USER +"\'" 
		//+" ORDER BY "+"\'id\' ASC LIMIT 1" +";";
		String query = "SELECT * FROM" + "\'" + TABLE_USER +"\'"+";";
		//cursor point to  a location in your results
		Cursor c = db.rawQuery(query, null);
		//move to the first row in your results
		c.moveToFirst();
		//	dbString = c.getString(c.getColumnIndex("name"));
		while(!c.isAfterLast()){
			if(c.getString(c.getColumnIndex("username")) != null){
				username +=c.getString(c.getColumnIndex("username")) + ",";		
			}
		
			if(c.getString(c.getColumnIndex("password")) != null){
				password +=c.getString(c.getColumnIndex("password"))+ ",";		
			}
			
			c.moveToNext();
		}
		myUser.set_userName(username);
		myUser.set_password(password);
		db.close();
		return myUser;
	}

}
