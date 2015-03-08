package com.model.pulsealert;

public class User {

	private int _id = 0;
	private int _age = 0;

	private String _userName = "";
	private String _name="";
	private String _password = "";
	private String _bday = "";
	private String _gender = "";
	private String _familyName1 = "";
	private String _famContactNumber1 = "";
	private String _familyName2 = "";
	private String _famContactNumber2 = "";
	private String _familyName3 = "";
	private String _famContactNumber3 = "";
	private String _doctor = "";
	private String _doctorContactNumber = "";

	public User(){
	}

	public User (String userName, String passWord){
		this._userName = userName;
		this._password = passWord; 
	}



	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_userName() {
		return _userName;
	}

	public void set_userName(String _userName) {
		this._userName = _userName;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public String get_familyName1() {
		return _familyName1;
	}

	public void set_familyName1(String _familyName1) {
		this._familyName1 = _familyName1;
	}

	public String get_famContactNumber1() {
		return _famContactNumber1;
	}

	public void set_famContactNumber1(String _famContactNumber1) {
		this._famContactNumber1 = _famContactNumber1;
	}

	public String get_familyName2() {
		return _familyName2;
	}

	public void set_familyName2(String _familyName2) {
		this._familyName2 = _familyName2;
	}

	public String get_famContactNumber2() {
		return _famContactNumber2;
	}

	public void set_famContactNumber2(String _famContactNumber2) {
		this._famContactNumber2 = _famContactNumber2;
	}

	public String get_familyName3() {
		return _familyName3;
	}

	public void set_familyName3(String _familyName3) {
		this._familyName3 = _familyName3;
	}

	public String get_famContactNumber3() {
		return _famContactNumber3;
	}

	public void set_famContactNumber3(String _famContactNumber3) {
		this._famContactNumber3 = _famContactNumber3;
	}

	public String get_doctor() {
		return _doctor;
	}

	public void set_doctor(String _doctor) {
		this._doctor = _doctor;
	}

	public String get_doctorContactNumber() {
		return _doctorContactNumber;
	}

	public void set_doctorContactNumber(String _doctorContactNumber) {
		this._doctorContactNumber = _doctorContactNumber;
	}

	public String get_bday() {
		return _bday;
	}

	public void set_bday(String _bday) {
		this._bday = _bday;
	}

	public int get_age() {
		return _age;
	}

	public void set_age(int _age) {
		this._age = _age;
	}

	public String get_gender() {
		return _gender;
	}

	public void set_gender(String _gender) {
		this._gender = _gender;
	}






}
