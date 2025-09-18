package com.amstech.std.system.repo.custom.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amstech.std.system.entity.User;
import com.amstech.std.system.repo.custom.UserCustomRepo;
import com.amstech.student.entity.City;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserCustomImplRepoCriteriaQuery implements UserCustomRepo {

	
	@Autowired
	private EntityManager entityManager;
	@Override
	public List<User> filterBy(Integer page, Integer size, String mobileNumber, Integer cityId, Integer status,
			String keyword) throws Exception {
	
		log.info("CriteriaQuery..");
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> userRoot = userCriteriaQuery.from(User.class);
		Join<User, City> userCityJoin = userRoot.join("city", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();
		if (cityId != null)
			predicateList.add(criteriaBuilder.equal(userCityJoin.get("id"), cityId));
		if (mobileNumber != null)
			predicateList.add(criteriaBuilder.equal(userRoot.get("mobileNumber"), mobileNumber));

		
		if (status != null)
			predicateList.add(criteriaBuilder.equal(userRoot.get("isActive"), status));
		
		if (keyword != null) {
			List<Predicate> keywordPredicateList = new ArrayList<>();
			keywordPredicateList.add(criteriaBuilder.like(userRoot.get("firstName"), "%" + keyword + "%"));
			keywordPredicateList.add(criteriaBuilder.like(userRoot.get("lastName"), "%" + keyword + "%"));
			keywordPredicateList.add(criteriaBuilder.like(userRoot.get("email"), "%" + keyword + "%"));
			keywordPredicateList.add(criteriaBuilder.like(userRoot.get("mobileNumber"), "%" + keyword + "%"));
			predicateList
					.add(criteriaBuilder.or(keywordPredicateList.toArray(new Predicate[keywordPredicateList.size()])));
		}

		Predicate finalPredicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
		userCriteriaQuery.where(finalPredicate);
		// 500 page 0 size 10 list sizer 10
		return entityManager.createQuery(userCriteriaQuery).setMaxResults(size).setFirstResult(page * size)
				.getResultList();

	}
	

	@Override
	public long countBy(String mobileNumber, Integer cityId, Integer status, String keyword) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
