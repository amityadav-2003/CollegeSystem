 package com.amstech.std.system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amstech.std.system.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Integer>{

}
