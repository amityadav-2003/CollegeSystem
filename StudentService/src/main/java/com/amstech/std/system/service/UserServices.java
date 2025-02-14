package com.amstech.std.system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amstech.std.system.entity.City;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.repo.CityRepo;
import com.amstech.std.system.repo.UserRepo;

@Service
public class UserServices {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CityRepo  cityRepo;
	
	public void signup(UserSignupRequestModel signupRequestModel) throws Exception {
		Optional<City> cityOptional =cityRepo.findById(signupRequestModel.getCityId());
		if(!cityOptional.isPresent()) {
			throw new Exception("city dose not exist");
		}
		User userEmail= userRepo.findByEmail(signupRequestModel.getEmail());
		
		if(userEmail!=null) {
			throw new Exception("email dose not exist please try with other email");
		}
		User userMob= userRepo.findByMobileNumber(signupRequestModel.getMobileNumber());
		if(userMob!=null) {
			throw new Exception("mobile number dose not exist please try with other mobilenumber");
		}
		User user=new User();
		user.setFirstName(signupRequestModel.getFirstName());
		user.setLastName(signupRequestModel.getLastName());
		user.setEmail(signupRequestModel.getEmail());
		user.setMobileNumber(signupRequestModel.getMobileNumber());
		user.setAddress(signupRequestModel.getAddress());
		user.setCityId(signupRequestModel.getCityId());
		User savedUser =userRepo.save(user);
		System.out.println("UserId"+savedUser.getId());
		
	}
	
}
