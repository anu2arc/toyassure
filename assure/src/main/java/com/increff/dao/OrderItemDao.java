package com.increff.dao;

import com.increff.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void insert(OrderItemPojo orderItemPojo) {
        entityManager.persist(orderItemPojo);
    }
}
