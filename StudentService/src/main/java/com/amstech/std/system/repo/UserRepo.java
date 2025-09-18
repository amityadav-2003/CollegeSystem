package com.amstech.std.system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amstech.std.system.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	
	@Query("select e from User e where e.mobileNumber=:mobileNumber")
	User findByMobileNumber(@Param("mobileNumber")String mobileNumber);

	@Query("select e from User e where e.firstName=:firstName")
	List<User>findByFirstName(@Param("firstName")String firstName);

	@Query("select e from User e where e.mobileNumber=:mobileNumber")
	Optional<User> findMobileNumber(@Param("mobileNumber")String mobileNumber);
   
	@Query("select e from User e where e.id=:id")
	Optional<User> findById(@Param("id") Integer id);
   
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.password=:password")
	User findByUsernamePassword(@Param("email") String email,@Param("password") String password);

	@Query("select e from User e where e.isActive=0")
	List<User> findAllUser(Pageable pageable);

	@Query("select count(e) from User e where e.isActive=0")
	long countAllUser();
	
	@Query("SELECT e FROM User e WHERE e.isActive = :status")
	List<User> findByStatus(@Param("status") Integer status);


}
