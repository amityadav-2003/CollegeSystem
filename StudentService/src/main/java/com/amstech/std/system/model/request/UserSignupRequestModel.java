package com.amstech.std.system.model.request;

import lombok.Data;

@Data
public class UserSignupRequestModel {
   private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	private String address;
    private String password;
    private int isActive;
    private int cityId;
    private String profilePhotoPath;
   
	
	
	

}
