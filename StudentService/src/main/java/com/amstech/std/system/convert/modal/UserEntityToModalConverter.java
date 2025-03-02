package com.amstech.std.system.convert.modal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.response.UserResponseModle;

@Component
public class UserEntityToModalConverter {

	public UserResponseModle findUser(User  user) {
		UserResponseModle userResponseModle = new UserResponseModle();
		userResponseModle.setId(user.getId());
		userResponseModle.setFirstName(user.getFirstName());
		userResponseModle.setLastName(user.getLastName());
		userResponseModle.setMobileNumber(user.getMobileNumber());
		userResponseModle.setAddress(user.getAddress());
		userResponseModle.setPassword(user.getPassword());
		
		userResponseModle.setCityId(user.getCityId());
		return userResponseModle;
		
	}

	public List<UserResponseModle> findAllUser(List<User>  users) {
		List<UserResponseModle> userResponseModles = new ArrayList<>();
		for(User user :users) {
			if(user.getIsActive()==0) {
				 continue; 
				 }
		UserResponseModle userResponseModle = new UserResponseModle();
		userResponseModle.setId(user.getId());
		userResponseModle.setFirstName(user.getFirstName());
		userResponseModle.setLastName(user.getLastName());
		userResponseModle.setMobileNumber(user.getMobileNumber());
		userResponseModle.setAddress(user.getAddress());
		userResponseModle.setPassword(user.getPassword());
		 
		userResponseModle.setCityId(user.getCityId());
		userResponseModles.add(userResponseModle);
		}
		return userResponseModles;
	}
}