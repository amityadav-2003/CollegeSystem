package com.amstech.std.system.model.request;

public class UserUpdateRequestModle {

	private int id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String address;
	private int cityId;
	private String password;
    private int isActive;
	
	public int getIsActive() {
	return isActive;
}
public void setIsActive(int isActive) {
	this.isActive = isActive;
}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	private String email;
	private boolean isEmailVerified;
	private boolean isEmailUpdate;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEmailVerified() {
		return isEmailVerified;
	}
	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	public boolean isEmailUpdate() {
		return isEmailUpdate;
	}
	public void setEmailUpdate(boolean isEmailUpdate) {
		this.isEmailUpdate = isEmailUpdate;
	}
		
	
	
}
