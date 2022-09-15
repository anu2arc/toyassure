package com.increff.dao;

import com.increff.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao {

    private static final String SELECT_BY_ORDERID="select bp from OrderItemPojo bp where orderId=:orderId";

    private static final String SELECT_BY_ID="select bp from OrderItemPojo bp where id=:id";

    @PersistenceContext
    private EntityManager entityManager;

    private TypedQuery<OrderItemPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, OrderItemPojo.class);
    }
    @Transactional
    public void insert(OrderItemPojo orderItemPojo) {
        entityManager.persist(orderItemPojo);
    }

    public List<OrderItemPojo> getItemsByOrderId(long orderId) {
        TypedQuery<OrderItemPojo> query=getQuery(SELECT_BY_ORDERID);
        query.setParameter("orderID",orderId);
        return query.getResultList();
    }

    public OrderItemPojo get(Long id) {
        TypedQuery<OrderItemPojo> query=getQuery(SELECT_BY_ID);
        query.setParameter("id",id);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
