package com.increff.dao;

import com.increff.pojo.BinPojo;
import com.increff.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public class OrderDao {

    private static final String SELECT_BY_ID="select bp from OrderPojo bp where id=:orderId";
    @PersistenceContext
    EntityManager entityManager;

    private TypedQuery<OrderPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, OrderPojo.class);
    }

    @Transactional
    public void add(OrderPojo orderPojo) {
        entityManager.persist(orderPojo);
    }

    public OrderPojo getOrder(long orderId) {
        TypedQuery<OrderPojo> query=getQuery(SELECT_BY_ID);
        query.setParameter("orderID",orderId);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
