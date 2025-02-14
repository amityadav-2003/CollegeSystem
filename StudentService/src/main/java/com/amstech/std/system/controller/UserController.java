package com.amstech.std.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.service.UserServices;

@Controller
@RequestMapping("/users")

public class UserController {
    @Autowired
	private UserServices userServices;
	
	public UserController() {
		System.out.println("UserController: Object Created");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/signup", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> signup(@RequestBody UserSignupRequestModel SignupRequestModel) {
		
		System.out.println("saving data with email"+SignupRequestModel.getEmail());
		try {
			userServices.signup(SignupRequestModel);
			return new ResponseEntity<Object>("data saving", HttpStatus.OK) ;
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("failed to saving data"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
		
		}

	@RequestMapping(method = RequestMethod.PUT, value = "/update", consumes = "application/json", produces = "application/json")
	public void update() {
		System.out.println("update");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login", consumes = "application/json", produces = "application/json")
	public void login() {
		System.out.println("login");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findById", produces = "application/json")
	public void findById() {
		System.out.println("findById");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = "application/json")
	public void findAll() {
		System.out.println("findAll");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByMobile", produces = "application/json")
	public void findByMobile() {
		System.out.println("findByMobile");
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteById", produces = "application/json")
	public void deleteById() {
		System.out.println("deleteById");
	}

}