package com.increff.dao;

import com.increff.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void add(OrderPojo orderPojo) {
        entityManager.persist(orderPojo);
    }
}
