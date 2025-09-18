//package com.amstech.std.system.repo.custom.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.amstech.std.system.entity.User;
//import com.amstech.std.system.repo.custom.UserCustomRepo;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import jakarta.persistence.TypedQuery;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class UserCustomImplRepo implements UserCustomRepo {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public List<User> filterBy(Integer page, Integer size, String mobileNumber, Integer cityId, Integer status, String keyword)
//            throws Exception {
//        String query = createQuery("e", mobileNumber, cityId, status, keyword);
//        log.info("Generated Query: {}", query);
//
//        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
//        setParam(typedQuery, page, size, mobileNumber, cityId, status, keyword);
//        return typedQuery.getResultList();
//    }
//
//    @Override
//    public long countBy(String mobileNumber, Integer cityId, Integer status, String keyword) throws Exception {
//        String query = createQuery("count(e)", mobileNumber, cityId, status, keyword);
//        log.info("Generated Query for Count: {}", query);
//
//        TypedQuery<Long> typedQuery = entityManager.createQuery(query, Long.class);
//        setParam(typedQuery, null, null, mobileNumber, cityId, status, keyword);
//        return typedQuery.getSingleResult();
//    }
//
//    private String createQuery(String type, String mobileNumber, Integer cityId, Integer status, String keyword) {
//        StringBuilder query = new StringBuilder("SELECT " + type + " FROM User e ");
//
//        boolean isWhereAdded = false;
//
//        if (mobileNumber != null) {
//            query.append(isWhereAdded ? " AND" : " WHERE");
//            query.append(" e.mobileNumber = :mobileNumber ");
//            isWhereAdded = true;
//        }
//
//        if (cityId != null) {
//            query.append(isWhereAdded ? " AND" : " WHERE");
//            query.append(" e.city.id = :cityId ");
//            isWhereAdded = true;
//        }
//
//        if (status != null) {
//            query.append(isWhereAdded ? " AND" : " WHERE");
//            query.append(" e.isActive = :status ");
//            isWhereAdded = true;
//            log.info("Filter By Status: {}", status);
//
//        }
//
//        if (keyword != null && !keyword.isEmpty()) {
//            query.append(isWhereAdded ? " AND" : " WHERE");
//            query.append(" (LOWER(e.firstName) LIKE LOWER(:keyword) OR LOWER(e.lastName) LIKE LOWER(:keyword) OR LOWER(e.email) LIKE LOWER(:keyword)) ");
//        }
//
//        return query.toString();
//    }
//
//    private void setParam(Query query, Integer page, Integer size, String mobileNumber, Integer cityId, Integer status, String keyword) {
//        if (mobileNumber != null) {
//            query.setParameter("mobileNumber", mobileNumber);
//        }
//
//        if (cityId != null) {
//            query.setParameter("cityId", cityId);
//        }
//
//        if (status != null) {
//            query.setParameter("status", status);
//        }
//
//        if (keyword != null && !keyword.isEmpty()) {
//            query.setParameter("keyword", "%" + keyword + "%");
//        }
//
//        if (page != null && size != null) {
//            query.setFirstResult(page * size);
//            query.setMaxResults(size);
//        }
//    }
//}
