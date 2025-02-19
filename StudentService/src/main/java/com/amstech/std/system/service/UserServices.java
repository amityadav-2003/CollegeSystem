package com.amstech.std.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amstech.std.system.entity.City;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserLoginRequestModel;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;
import com.amstech.std.system.model.response.UserResponseModle;
import com.amstech.std.system.repo.CityRepo;
import com.amstech.std.system.repo.UserRepo;

import jakarta.validation.Valid;

@Service
public class UserServices<UserResponseModel> {

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
			throw new Exception("email does not exist please try with other email");
		}
		
		User userMob= userRepo.findByMobileNumber(signupRequestModel.getMobileNumber());
		
		if(userMob!=null) {
			throw new Exception("mobile number dose not exist please try with other mobilenumber");
		}
	
			
		User user=new User();
		user.setId(signupRequestModel.getId());
		user.setFirstName(signupRequestModel.getFirstName());
		user.setLastName(signupRequestModel.getLastName());
		user.setEmail(signupRequestModel.getEmail());
		user.setMobileNumber(signupRequestModel.getMobileNumber());
		user.setAddress(signupRequestModel.getAddress());
		user.setCityId(signupRequestModel.getCityId());
		user.setPassword(signupRequestModel.getPassword());
		user.setIsActive(signupRequestModel.getIsActive());
		User savedUser =userRepo.save(user);
		System.out.println("UserId"+savedUser.getId());
		
	}
	
	public void update( UserUpdateRequestModle userUpdateRequestModle) throws Exception {
		
		 Optional<User> useroptional = userRepo.findById(userUpdateRequestModle.getId());
		 
		  if ( ! useroptional.isPresent() ) {
			  throw new Exception("user does not  exist");
		}
		  User user=useroptional.get();
		  if(userUpdateRequestModle.isEmailUpdate()) {
			  if(!userUpdateRequestModle.isEmailVerified()) {
				  throw new Exception("email id is not verified please verified First");
			  }
			  
			  User userByEmail=userRepo.findByEmail(userUpdateRequestModle.getEmail());
			  if(userByEmail !=null) {
				  throw new Exception("User email already");
			  }
			  user.setEmail(userUpdateRequestModle.getEmail());
		  }
		
		//user.setId(userUpdateRequestModle.getId());
		user.setFirstName(userUpdateRequestModle.getFirstName());
		user.setLastName(userUpdateRequestModle.getLastName());
		user.setEmail(userUpdateRequestModle.getEmail());
		user.setMobileNumber(userUpdateRequestModle.getMobileNumber());
		user.setAddress(userUpdateRequestModle.getAddress());
		user.setCityId(userUpdateRequestModle.getCityId());
		user.setIsActive(userUpdateRequestModle.getIsActive());
		user.setPassword(userUpdateRequestModle.getPassword());
	
		
		userRepo.save(user);
	//	System.out.println("UserId saved :"+ savedUser.getId());
  }

	public UserResponseModle findById(Integer id) throws Exception {
		
		 Optional<User> useroptional = userRepo.findById(id);
		 
		  if ( ! useroptional.isPresent() ) {
			  throw new Exception("user does not  exist");
		}
		  User user =useroptional.get();
		  if(user.getIsActive() ==0) {
			  throw new Exception("user is deactivated");
		  }
		
		UserResponseModle responseModle= new UserResponseModle();
		
		responseModle.setId(user.getId());
		responseModle.setFirstName(user.getFirstName());
		responseModle.setLastName(user.getLastName());
		responseModle.setAddress(user.getAddress());
		responseModle.setMobileNumber(user.getMobileNumber());
		return responseModle;
	}

	public List<UserResponseModle> findAll()  {
		List<User> userList= userRepo.findAll();
		
		List<UserResponseModle> userResponseModles =new ArrayList<>();
		for(User user :userList) {
			 if(user.getIsActive() ==0) {
				 continue;  }
	  UserResponseModle responseModle= new UserResponseModle();
       
		responseModle.setId(user.getId());
		responseModle.setFirstName(user.getFirstName());
		responseModle.setLastName(user.getLastName());
		responseModle.setAddress(user.getAddress());
		responseModle.setMobileNumber(user.getMobileNumber());
		responseModle.setPassword(user.getPassword());
		userResponseModles.add(responseModle);
		
		}
		
		return userResponseModles;
	}

	public UserResponseModle findMobileNumber(String mobileNumber) throws Exception {
		 Optional<User> useroptional = userRepo.findMobileNumber(mobileNumber);
		 if ( ! useroptional.isPresent() ) {
			  throw new Exception("user does not  exist");
		}
		User user =useroptional.get();
		UserResponseModle responseModle= new UserResponseModle();
		
		responseModle.setId(user.getId());
		responseModle.setFirstName(user.getFirstName());
		responseModle.setLastName(user.getLastName());
		responseModle.setAddress(user.getAddress());
		responseModle.setMobileNumber(user.getMobileNumber());
		responseModle.setPassword(user.getPassword());
	
		
		return responseModle;
	}

	public  void deleteById(Integer id) throws Exception {
		
		 Optional<User> useroptional = userRepo.findById(id);
		 
		  if ( !useroptional.isPresent() ) {
			  throw new Exception("user does not  exist");
		}
		userRepo.deleteById(id);
			
		  }
	public  void softdeleteById(Integer id) throws Exception {
		
		 Optional<User> useroptional = userRepo.findById(id);
		 
		  if ( !useroptional.isPresent() ) {
			  throw new Exception("user does not  exist");
		}
		  User user =useroptional.get();
		  if(user.getIsActive() == 0) {
			  throw new Exception("user alreaday deleted");
		  }
		user.setIsActive(0);
		
		userRepo.save(user);
			
		  }

	public void login(UserLoginRequestModel userLoginRequestModel) throws Exception {
		 User user = userRepo.findByUsernamePassword(userLoginRequestModel.getEmail(), userLoginRequestModel.getPassword());

		
		  if (!user.getEmail().equals(userLoginRequestModel.getEmail())) {
			 throw new Exception("wrong email and password");
		 }
		  if(user.getIsActive()==0) {
		       throw new Exception("this usser is deactivate .....................");
		}	
		  
		  if (! user.getPassword().equals(userLoginRequestModel.getPassword())) {
			  throw new Exception("incorrect email id and password");
	            }
		 }
	

}
