package com.amstech.std.system.model.response;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class UserResponseModle {

        private int id;
		private String firstName;
		private String lastName;
		private String address;
		private String mobileNumber;
		private String  password;
		private int  cityId;
		private String cityName;
		
		
		
}
