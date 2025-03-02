package com.amstech.std.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.amstech.std.system.model.request.UserLoginRequestModel;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;
import com.amstech.std.system.model.response.UserResponseModle;
import com.amstech.std.system.service.UserServices;

@Controller
@RequestMapping("/users")

public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserServices userServices;

	public UserController() {
		LOGGER.info("UserController: Object Created");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/signup", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> signup(@RequestBody UserSignupRequestModel SignupRequestModel) {

		LOGGER.info("Attempting to register user with email: {}", SignupRequestModel.getEmail());

		try {
			userServices.signup(SignupRequestModel);
			return new ResponseEntity<Object>("User registered successfully", HttpStatus.OK);

		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("User registered failed:{}", e.getMessage(), e);
			return new ResponseEntity<Object>("User registered failed" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> update(@RequestBody UserUpdateRequestModle userUpdateRequestModle) {
		LOGGER.info("updateing user detail with id {}", userUpdateRequestModle.getId());
		try {
			userServices.update(userUpdateRequestModle);
			return new ResponseEntity<Object>("update successfuly", HttpStatus.OK);
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("failed to update user due to:{}", e.getMessage(), e);
			return new ResponseEntity<Object>("Failed to update user due to:" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> login(@RequestBody UserLoginRequestModel userLoginRequestModel) {

		LOGGER.info("login:{}", userLoginRequestModel.getPassword());
		try {
			userServices.login(userLoginRequestModel);
			return new ResponseEntity<Object>("login succesfully", HttpStatus.OK);

		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("Login failed:{}", e.getMessage(), e);
			return new ResponseEntity<Object>("login failed:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/findById", produces = "application/json")
	public ResponseEntity<Object> findById(@RequestParam("id") Integer id) {
		LOGGER.info("Feteching user detail:{}" + id);
		try {

			UserResponseModle userResponseModle = userServices.findById(id);
			return new ResponseEntity<Object>(userResponseModle, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("failed to find user due to:{}", e.getMessage(), e);
			return new ResponseEntity<Object>("Failed to find user due to:" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = "application/json")
	public ResponseEntity<Object> findAll() {
		LOGGER.info("Feteching  all user detail");
		try {
			List<UserResponseModle> userResponseModle = userServices.findAll();

			return new ResponseEntity<Object>(userResponseModle, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("failed to FIND user data:{}", e.getMessage(), e);
			return new ResponseEntity<Object>("Failed to find user due to:" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByMobile", produces = "application/json")
	public ResponseEntity<Object> findByMobile(@RequestParam("mobileNumber") String mobileNumber) {
		LOGGER.info("Feteching user detail by mobile number :{}", mobileNumber);
		try {

			UserResponseModle userResponseModle = userServices.findMobileNumber(mobileNumber);
			return new ResponseEntity<Object>(userResponseModle, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("failed to find due to:{}", e.getMessage(), e);
			return new ResponseEntity<Object>("Failed to find user due to:" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteById/{id}", produces = "application/json")
	public ResponseEntity<Object> deleteById(@PathVariable("id") Integer id) {

	LOGGER.info("deleteById:{}",id);
		try {
			 userServices.deleteById(id);
			return new ResponseEntity<Object>("delete succesfully",HttpStatus.OK);
        } catch (Exception e) {
        	LOGGER.error("Failed to delete user: {}", e.getMessage(), e);
			return new ResponseEntity<Object>("Failed to delete user due to:"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/softdelete/{id}", produces = "application/json")
	public ResponseEntity<Object> softdeleteById(@PathVariable("id") Integer id) {

		LOGGER.info("deleteById:{}", id);
		try {
			userServices.softdeleteById(id);
			return new ResponseEntity<Object>("delete succesfully", HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Failed to delete user with ID {}: {}", id, e.getMessage(), e);

			return new ResponseEntity<Object>("Failed to find user due to:" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}