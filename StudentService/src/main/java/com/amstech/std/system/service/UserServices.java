package com.amstech.std.system.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amstech.std.system.convert.modal.UserEntityToModalConverter;
import com.amstech.std.system.converter.entity.UserModalToEntityConverter;
import com.amstech.std.system.entity.City;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserLoginRequestModel;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;
import com.amstech.std.system.model.response.UserResponseModle;
import com.amstech.std.system.repo.CityRepo;
import com.amstech.std.system.repo.UserRepo;

@Service
public class UserServices<UserResponseModel> {

	private final Logger LOGGER =LoggerFactory.getLogger(UserServices.class);

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CityRepo  cityRepo;
	
    @Autowired
	private  UserModalToEntityConverter userModalToEntityConverter;
    @Autowired
    private UserEntityToModalConverter userEntityToModalConverter;
	
	public void signup(UserSignupRequestModel usersignupRequestModel) throws Exception {
		LOGGER.debug("Fetching city by cityId:{}",usersignupRequestModel.getCityId());
		
		Optional<City> cityOptional =cityRepo.findById(usersignupRequestModel.getCityId());
		
		if(!cityOptional.isPresent()) {
			throw new Exception("city dose not exist");
		}
		
		User userEmail= userRepo.findByEmail(usersignupRequestModel.getEmail());
		
		if(userEmail!=null) {
			throw new Exception("email already exist please try with other email");
		}
		
		User userMob= userRepo.findByMobileNumber(usersignupRequestModel.getMobileNumber());
		
		if(userMob!=null) {
			throw new Exception("mobile numberalready  exist please try with other mobilenumber");
		}
	
		  User user = userModalToEntityConverter.saveUser(usersignupRequestModel);
		    User savedUser = userRepo.save(user);
		
		LOGGER.info("UserId:{}",savedUser.getId());
		
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
				  throw new Exception("Email is already in use by another user");
			  }
			  user.setEmail(userUpdateRequestModle.getEmail());
		  }
		

		  User userupdate = userModalToEntityConverter.updateUser(userUpdateRequestModle,user);
		    User savedUser = userRepo.save(userupdate);
		
		
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
		
		  UserResponseModle userResponse = userEntityToModalConverter.findUser(user);

		return userResponse ;
	}

	public List<UserResponseModle> findAll()  {
		List<User> userList= userRepo.findAll();
		
		List<UserResponseModle> userResponseModles =new ArrayList<>();
		List<UserResponseModle> userResponse = userEntityToModalConverter.findAllUser(userList);
		
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
		LOGGER.info("User with ID: {} has been soft deleted successfully", id);
		  }

	public void login(UserLoginRequestModel userLoginRequestModel) throws Exception {
		 User user = userRepo.findByUsernamePassword(userLoginRequestModel.getEmail(), userLoginRequestModel.getPassword());

		
		  if (!user.getEmail().equals(userLoginRequestModel.getEmail())) {
			 throw new Exception("wrong email and password");
		 }
		  if(user.getIsActive()==0) {
			  
		       throw new Exception("this user is deactivate .....................");
		}	
		  
		  if (! user.getPassword().equals(userLoginRequestModel.getPassword())) {
			  throw new Exception("incorrect email id and password");
	            }
		  LOGGER.info("User with email {} logged in successfully", userLoginRequestModel.getEmail());
		 }
    


}
