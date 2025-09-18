package com.amstech.std.system.model.request;

import lombok.Data;

@Data
public class UserLoginCaptureRequestModel {
	private String email;
	private String mobileNumber;
	
    private String password;
    
    private String captchaResponse;

		
}
