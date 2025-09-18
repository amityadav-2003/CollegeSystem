package com.amstech.std.system.converter.entity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amstech.std.system.entity.City;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;
import com.amstech.std.system.repo.CityRepo;


@Component
public class UserModalToEntityConverter {

	@Autowired
	private CityRepo cityRepo;

public  User  saveUser(UserSignupRequestModel userSignupRequestModel) throws Exception {
	Optional<City> optionalCity = cityRepo.findById(userSignupRequestModel.getCityId());

	if (optionalCity.isEmpty()) {
		throw new Exception("The city does not exist.");
	}
	User user = new User();
	user.setFirstName(userSignupRequestModel.getFirstName());
	user.setLastName(userSignupRequestModel.getLastName());
	user.setEmail(userSignupRequestModel.getEmail());
	user.setAddress(userSignupRequestModel.getAddress());
	user.setCity(optionalCity.get());
		user.setMobileNumber(userSignupRequestModel.getMobileNumber());
	user.setIsActive(1);
	user.setPassword(userSignupRequestModel.getPassword());
    

	return user;
	
}

public User updateUser(UserUpdateRequestModle userUpdateRequestModle, User user) throws Exception {
	
	Optional<City> optionalCity = cityRepo.findById(userUpdateRequestModle.getCityId());

	if (optionalCity.isEmpty()) {
		throw new Exception("The city does not exist.");
	}
	user.setFirstName(userUpdateRequestModle.getFirstName());
	user.setLastName(userUpdateRequestModle.getLastName());
	user.setEmail(userUpdateRequestModle.getEmail());
	user.setMobileNumber(userUpdateRequestModle.getMobileNumber());
	user.setAddress(userUpdateRequestModle.getAddress());
	//user.setCity(userUpdateRequestModle.getCityId());
	user.setCity(optionalCity.get());
	
	user.setIsActive(userUpdateRequestModle.getIsActive());
	user.setPassword(userUpdateRequestModle.getPassword());

	

	return user;
	
}
	
	
}
