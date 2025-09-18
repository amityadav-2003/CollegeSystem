package com.amstech.std.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.amstech.std.system.convert.modal.UserEntityToModalConverter;
import com.amstech.std.system.converter.entity.UserModalToEntityConverter;
import com.amstech.std.system.entity.City;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserLoginCaptureRequestModel;
import com.amstech.std.system.model.request.UserLoginRequestModel;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;
import com.amstech.std.system.model.response.UserResponseModle;
import com.amstech.std.system.repo.CityRepo;
import com.amstech.std.system.repo.UserRepo;
import com.amstech.std.system.repo.custom.UserCustomRepo;
import com.amstech.student.util.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Transactional
@Service

public class UserServices {

	private final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CityRepo cityRepo;
	
	@Autowired
	@Qualifier("userCustomImplRepoCriteriaQuery")
	private UserCustomRepo userCustomRepo;


	@Autowired
	private UserModalToEntityConverter userModalToEntityConverter;
	@Autowired
	private UserEntityToModalConverter userEntityToModalConverter;

	public UserResponseModle signup(UserSignupRequestModel usersignupRequestModel) throws Exception {
		LOGGER.debug("Fetching city by cityId:{}", usersignupRequestModel.getCityId());
		Optional<City> cityOptional = cityRepo.findById(usersignupRequestModel.getCityId());
		if (!cityOptional.isPresent()) {
			throw new Exception("city dose not exist");
		}
		User userEmail = userRepo.findByEmail(usersignupRequestModel.getEmail());
		if (userEmail != null) {
			throw new Exception("email already exist please try with other email");
		}
		User userMob = userRepo.findByMobileNumber(usersignupRequestModel.getMobileNumber());
		if (userMob != null) {
			throw new Exception("mobile numberalready  exist please try with other mobilenumber");
		}
		
		User user = userModalToEntityConverter.saveUser(usersignupRequestModel);
		User savedUser = userRepo.save(user);
		UserResponseModle userResponse = userEntityToModalConverter.findUser(user);
		
		
		LOGGER.info("UserId:{}", savedUser.getId());
		return userResponse ;
	}

	public UserResponseModle update(UserUpdateRequestModle userUpdateRequestModle) throws Exception {

		Optional<User> useroptional = userRepo.findById(userUpdateRequestModle.getId());

		if (!useroptional.isPresent()) {
			throw new Exception("user does not  exist");
		}
		User user = useroptional.get();
		if (userUpdateRequestModle.isEmailUpdate()) {
			if (!userUpdateRequestModle.isEmailVerified()) {
				throw new Exception("email id is not verified please verified First");
			}

			User userByEmail = userRepo.findByEmail(userUpdateRequestModle.getEmail());
			if (userByEmail != null) {
				throw new Exception("Email is already in use by another user");
			}
			user.setEmail(userUpdateRequestModle.getEmail());
		}

		User userupdate = userModalToEntityConverter.updateUser(userUpdateRequestModle, user);
		User savedUser = userRepo.save(userupdate);

		UserResponseModle userResponse = userEntityToModalConverter.findUser(user);

		return userResponse;
	}

	public UserResponseModle findById(Integer id) throws Exception {

		Optional<User> useroptional = userRepo.findById(id);

		if (!useroptional.isPresent()) {
			throw new Exception("user does not  exist");
		}
		User user = useroptional.get();
		if (user.getIsActive() == 0) {
			throw new Exception("user is deactivated");
		}

		UserResponseModle userResponse = userEntityToModalConverter.findUser(user);

		return userResponse;
	}

	public List<UserResponseModle> findAll(Integer page, Integer size) throws Exception {

		List<User> userList = userRepo.findAllUser(PageRequest.of(page, size));

		return userEntityToModalConverter.findAllUser(userList);
	}

	public long countAllUser() throws Exception {
		return userRepo.countAllUser();
	}

	public UserResponseModle findMobileNumber(String mobileNumber) throws Exception {
		Optional<User> useroptional = userRepo.findMobileNumber(mobileNumber);

		if (!useroptional.isPresent()) {
			throw new Exception("user does not  exist");
		}

		User user = useroptional.get();

		UserResponseModle responseModle = new UserResponseModle();

//		responseModle.setId(user.getId());
//		responseModle.setFirstName(user.getFirstName());
//		responseModle.setLastName(user.getLastName());
//		responseModle.setAddress(user.getAddress());
//		responseModle.setMobileNumber(user.getMobileNumber());
//		responseModle.setPassword(user.getPassword());
//		responseModle.setCityId(user.getCity());
		UserResponseModle userResponse = userEntityToModalConverter.findUser(user);

		return userResponse;
	}

	public void deleteById(Integer id) throws Exception {

		Optional<User> useroptional = userRepo.findById(id);

		if (!useroptional.isPresent()) {
			throw new Exception("user does not  exist");
		}

		userRepo.deleteById(id);

	}

	public void softdeleteById(Integer id) throws Exception {

		Optional<User> useroptional = userRepo.findById(id);

		if (!useroptional.isPresent()) {
			throw new Exception("user does not  exist");
		}
		User user = useroptional.get();
		if (user.getIsActive() == 0) {
			throw new Exception("user alreaday deleted");
		}
		user.setIsActive(0);

		userRepo.save(user);
		LOGGER.info("User with ID: {} has been soft deleted successfully", id);
	}

	public UserResponseModle login(UserLoginRequestModel userLoginRequestModel) throws Exception {
		User user = userRepo.findByUsernamePassword(userLoginRequestModel.getEmail(),
				userLoginRequestModel.getPassword());

		if (!user.getEmail().equals(userLoginRequestModel.getEmail())) {
			throw new Exception("wrong email and password");
		}
		if (user.getIsActive() == 0) {
			throw new Exception("Your account has been deactivated. Please contact the administrator for assistance.");
		}

		if (!user.getPassword().equals(userLoginRequestModel.getPassword())) {
			throw new Exception("incorrect email id and password");
		}
		LOGGER.info("User with email {} logged in successfully", userLoginRequestModel.getEmail());

		return userEntityToModalConverter.findUser(user);
	}
	
	//StatusChange
	public void chagneStatus(Integer id, Integer status) throws Exception {
		Optional<User> userOptional = userRepo.findById(id);
		if (userOptional.isEmpty()) {
			throw new Exception("The user account does not exist.");
		}
		User user = userOptional.get();

		if (user.getIsActive() == 1) {
			throw new Exception("This user account has already been deleted.");
		}

		user.setIsActive(status);
	
		userRepo.save(user);
	}
	

	public List<UserResponseModle> filterBy(Integer page, Integer size, String mobileNumber, Integer cityId, Integer status, String keyword) throws Exception {
		List<User> userList = userCustomRepo.filterBy(page, size, mobileNumber, cityId, status, keyword);
		return userEntityToModalConverter.findAllUser(userList);
	}
	public long countBy(String mobileNumber, Integer cityId, Integer status, String keyword) throws Exception {
		return userCustomRepo.countBy(mobileNumber, cityId,status, keyword);
	}
	
	
	 public void exportUsersToPdf(HttpServletResponse response) throws IOException {
	        List<User> users = userRepo.findAll();
	        PdfGenerator pdfGenerator = new PdfGenerator(users);
	        pdfGenerator.generate(response);
	    }

	
	
	 
	 
}
