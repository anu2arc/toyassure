package com.increff.dao;


import com.increff.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public class InventoryDao {
    private static final String SELECT_BY_ID="select ip from InventoryPojo ip where globalSkuId=:globalSkuId";
    @PersistenceContext
    private EntityManager entityManager;
    private TypedQuery<InventoryPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, InventoryPojo.class);
    }

    @Transactional
    public void add(InventoryPojo inventoryPojo){
        entityManager.persist(inventoryPojo);
    }
    public InventoryPojo get(Long globalSkuId) {
        TypedQuery<InventoryPojo> query=getQuery(SELECT_BY_ID);
        query.setParameter("globalSkuId",globalSkuId);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
