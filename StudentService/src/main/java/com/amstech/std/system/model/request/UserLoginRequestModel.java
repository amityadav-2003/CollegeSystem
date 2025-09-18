package com.amstech.std.system.model.request;

import lombok.Data;

@Data
public class UserLoginRequestModel {
	private String email;
	private String mobileNumber;
	
    private String password;
    
    private String captchaResponse;

		
}
