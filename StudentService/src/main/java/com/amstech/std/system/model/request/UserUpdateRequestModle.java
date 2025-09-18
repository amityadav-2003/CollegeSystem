package com.amstech.std.system.model.request;

import lombok.Data;

@Data
public class UserUpdateRequestModle {

	private int id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String address;
	private int cityId;
	private String password;
    private int isActive;

	private String email;
	private boolean isEmailVerified;
	private boolean isEmailUpdate;
	

	
	
}
