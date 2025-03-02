package com.amstech.std.system.converter.entity;

import org.springframework.stereotype.Component;

import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;

@Component
public class UserModalToEntityConverter {

public  User  saveUser(UserSignupRequestModel userSignupRequestModel) {
	
	User user = new User();
	user.setFirstName(userSignupRequestModel.getFirstName());
	user.setLastName(userSignupRequestModel.getLastName());
	user.setEmail(userSignupRequestModel.getEmail());
	user.setAddress(userSignupRequestModel.getAddress());
	user.setCityId(userSignupRequestModel.getCityId());
	user.setMobileNumber(userSignupRequestModel.getMobileNumber());
	
	user.setIsActive(1);
	user.setPassword(userSignupRequestModel.getPassword());


	return user;
	
}

public User updateUser(UserUpdateRequestModle userUpdateRequestModle, User user) {
	
	user.setFirstName(userUpdateRequestModle.getFirstName());
	user.setLastName(userUpdateRequestModle.getLastName());
	user.setEmail(userUpdateRequestModle.getEmail());
	user.setMobileNumber(userUpdateRequestModle.getMobileNumber());
	user.setAddress(userUpdateRequestModle.getAddress());
	user.setCityId(userUpdateRequestModle.getCityId());
	user.setIsActive(userUpdateRequestModle.getIsActive());
	user.setPassword(userUpdateRequestModle.getPassword());

	

	return user;
	
}
	
	
}
