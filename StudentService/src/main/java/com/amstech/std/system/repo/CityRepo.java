package com.amstech.std.system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amstech.std.system.entity.City;

public interface CityRepo  extends JpaRepository<City, Integer>{

}
