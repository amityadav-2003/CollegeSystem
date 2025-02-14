package com.amstech.std.system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amstech.std.system.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("select e from User e where e.email=:email")
	User findByEmail(@Param("email")String email);
	
	@Query("select e from User e where e.mobileNumber=:mobileNumber")
	User findByMobileNumber(@Param("mobileNumber")String mobileNumber);

	@Query("select e from User e where e.firstName=:firstName")
	List<User>findByFirstName(@Param("firstName")String firstName);
	
	

}
