package com.amstech.std.system.repo.custom;

import java.util.List;

import com.amstech.std.system.entity.User;



public interface UserCustomRepo {

public List<User> filterBy(Integer page, Integer size, String mobileNumber, Integer cityId, Integer status, String keyword) throws Exception;
	
	public long countBy(String mobileNumber, Integer cityId,Integer status, String keyword) throws Exception;

	
}
