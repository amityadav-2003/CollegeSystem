package com.amstech.std.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amstech.std.response.RestResponse;
import com.amstech.std.system.entity.User;
import com.amstech.std.system.model.request.UserLoginRequestModel;
import com.amstech.std.system.model.request.UserSignupRequestModel;
import com.amstech.std.system.model.request.UserUpdateRequestModle;
import com.amstech.std.system.model.response.UserResponseModle;
import com.amstech.std.system.repo.UserRepo;
import com.amstech.std.system.service.ExcelService;
import com.amstech.std.system.service.FileService;
import com.amstech.std.system.service.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")

public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserServices userServices;

	@Autowired
	private FileService fileService;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private ExcelService excelService;

	public UserController() {
		LOGGER.info("UserController: Object Created");
	}

	@Operation(summary = "You can use this method for user/student signup", description = "This is desc")
	@RequestMapping(method = RequestMethod.POST, value = "/signup", consumes = "application/json", produces = "application/json")
	public RestResponse signup(@RequestBody UserSignupRequestModel SignupRequestModel) {

		LOGGER.info("Saving user data with email: {}", SignupRequestModel.getEmail());

		try {
			userServices.signup(SignupRequestModel);
			RestResponse.build().withData(SignupRequestModel);
			return RestResponse.success("555").withMessage("user screated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("User registered failed:{}", e.getMessage(), e);
			return RestResponse.build().withError("5555").withMessage("failed to user due to:" + e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/update", consumes = "application/json", produces = "application/json")
	public RestResponse update(@RequestBody UserUpdateRequestModle userUpdateRequestModle) {
		LOGGER.info("updateing  user detail with id {}", userUpdateRequestModle.getId());
		try {
			UserResponseModle userResponseModle =	userServices.update(userUpdateRequestModle);
			return RestResponse.build().withSuccess("User updated successfully", userResponseModle);
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("failed to update user due to:{}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login", consumes = "application/json", produces = "application/json")
	public RestResponse login(@RequestBody UserLoginRequestModel userLoginRequestModel) {

		LOGGER.info("login:{}", userLoginRequestModel.getPassword());
		try {
			UserResponseModle userResponseModle = userServices.login(userLoginRequestModel);
			
			return RestResponse.build().withSuccess("User login successfully", userResponseModle);

		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error("Login failed:{}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/findById", produces = "application/json")
	public RestResponse findById(@RequestParam("id") Integer id) {
		LOGGER.info("Feteching user detail:{}" + id);
		try {

			UserResponseModle userResponseModle = userServices.findById(id);
			return RestResponse.build().withSuccess("User found successfully", userResponseModle);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("failed to find user due to:{}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}

	}
//FindAll
	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = "application/json")
	public RestResponse findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		LOGGER.info("Fetching all user");
		try {
			
			List<UserResponseModle> userResponseModels = userServices.findAll(page, size);
			
			long totalRecord = userServices.countAllUser();
			
			RestResponse.build();
			return RestResponse.success("User list found successfully").withTotalRecords(totalRecord)
					.withPageNumber(page).withPageSize(size).withData(userResponseModels);
		} catch (Exception e) {
			LOGGER.error("Failed to find user list due to: {}", e.getMessage(), e);
			RestResponse.build();
			return RestResponse.error(e.getMessage());
		}
	}
////find all
//	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = "application/json")
//	public ResponseEntity<Object> findAll() {
//		LOGGER.info("Feteching  all user detail");
//		try {
//		
//			List<UserResponseModle> userResponseModle = userServices.findAllUser(page, size);
//
//			return new ResponseEntity<Object>(userResponseModle, HttpStatus.OK);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("failed to FIND user data:{}", e.getMessage(), e);
//			return new ResponseEntity<Object>("Failed to find user due to:" + e.getMessage(),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByMobile", produces = "application/json")
	public RestResponse findByMobile(@RequestParam("mobileNumber") String mobileNumber) {
		LOGGER.info("Feteching user detail by mobile number :{}", mobileNumber);
		try {

			UserResponseModle userResponseModle = userServices.findMobileNumber(mobileNumber);
			return RestResponse.build().withSuccess("User found successfully").withData(userResponseModle);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("failed to find due to:{}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());

		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteById/{id}", produces = "application/json")
	public RestResponse deleteById(@PathVariable("id") Integer id) {

		LOGGER.info("deleteById:{}", id);
		try {
			userServices.deleteById(id);
			return RestResponse.build().withSuccess("User deleted successfully");
		} catch (Exception e) {
			LOGGER.error("Failed to delete user: {}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/softdelete/{id}", produces = "application/json")
	public RestResponse softdeleteById(@PathVariable("id") Integer id) {

		LOGGER.info("deleteById:{}", id);
		try {
			userServices.softdeleteById(id);
			return RestResponse.build().withSuccess("User deleted successfully");
		
		} catch (Exception e) {
			LOGGER.error("Failed to delete user with ID {}: {}", id, e.getMessage(), e);

			return RestResponse.build().withError(e.getMessage());
		}
	}
	
//CHANGE STATUS
	@RequestMapping(method = RequestMethod.DELETE, value = "/changeStatus", produces = "application/json")
	public RestResponse chagneStatus(@RequestParam("userId") Integer id, @RequestParam("status") Integer status) {
		LOGGER.info("Changing status for user by userId: {}, status: {}", id, status);
		try {
			userServices.chagneStatus(id, status);
			if (status == 0)
				return RestResponse.build().withSuccess("User re-activated successfully");
			else
				return RestResponse.build().withSuccess("User de-activated successfully");

		} catch (Exception e) {
			LOGGER.error("Failed to delete user due to: {}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}
	
// USER SIGNUP	
	@RequestMapping(method = RequestMethod.POST, value = "/signupWithMiltipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public RestResponse signupWithMiltipart(@RequestParam("userRequestModelJson") String userRequestModelJson,
			@RequestParam("profilePhotoFile") MultipartFile profilePhotoFile) throws IOException {
		String filePath = null;
		try {
			UserSignupRequestModel requestModel = objectMapper.readValue(userRequestModelJson,
					UserSignupRequestModel.class);
			LOGGER.info("Saving user data with email: {}", requestModel.getEmail());

			if (profilePhotoFile != null) {
				LOGGER.info("File name: {} with file size: {} byts", profilePhotoFile.getOriginalFilename(),
						profilePhotoFile.getSize());
				if (profilePhotoFile.getSize() > fileService.getFileMaxSize())
					throw new Exception("File size can not be gretter then: " + fileService.getFileMaxSize() + "byts");
				
				filePath = fileService.saveFile(profilePhotoFile.getBytes(), "users", FilenameUtils.getExtension(profilePhotoFile.getOriginalFilename()));

			}
			requestModel.setProfilePhotoPath(filePath);

		
			UserResponseModle responseModel = userServices.signup(requestModel);
			return RestResponse.build().withSuccess("User created successfully", responseModel);
		} catch (Exception e) {
			FileUtils.delete(new File(filePath));
			LOGGER.error("Failed to save user due to: {}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/filterBy", produces = "application/json")
	public RestResponse filterBy(@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "size", required = true) Integer size,
			@RequestParam(value = "mobileNumber", required = false) String mobileNumber,
			@RequestParam(value = "cityId", required = false) Integer cityId,
					@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "keyword", required = false) String keyword) {
		LOGGER.info(
				"Fetching user by fillter page: {}, size: {}, mobileNumber: {}, cityId: {},status: {}, keyword: {}",
				page, size, mobileNumber, cityId,status, keyword);
		try {
			List<UserResponseModle> userResponseModels = userServices.filterBy(page, size, mobileNumber,cityId,status, keyword);
			long totalRecord = userServices.countBy(mobileNumber, cityId,status, keyword);
			return RestResponse.build().withSuccess("User list found successfully").withTotalRecords(totalRecord)
					.withPageNumber(page).withPageSize(size).withData(userResponseModels);
		} catch (Exception e) {
			LOGGER.error("Failed to find user list due to: {}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}

	  @Operation(summary = "Export users in PDF file")
	
	@GetMapping("/pdf/all")
    public void exportToPdf(HttpServletResponse response) throws IOException {
       
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=users.pdf");
     
          this.userServices.exportUsersToPdf(response);
    }
	
	  @Operation(summary = "export users in Excel file")

	@GetMapping("/export/users")
    public void exportUsers(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        
        List<User> users = userRepository.findAll();
        
       
        excelService.usersToExcel(users, response.getOutputStream());
    }
	
	
  @Operation(summary = "Import users from Excel file")
	@PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RestResponse importUsers(@RequestParam("file") MultipartFile file) {
	    try {
	        excelService.importUsersFromExcel(file.getInputStream());
	        RestResponse.build().withData(file);
			return RestResponse.success("Users imported successfully!");
	    } catch (Exception e) {
	        // Log error if needed
	        return RestResponse.error(e).withError("Failed to import users: " + e.getMessage());
	    }
	}

  
  
  
 
}