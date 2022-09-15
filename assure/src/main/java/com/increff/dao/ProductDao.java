package com.increff.dao;

import com.increff.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao {

    private static final String SELECT_ALL="select pp from ProductPojo pp";
    private static final String SELECT_BY_GID="select pp from ProductPojo pp where globalSkuId=:globalSkuId";
    private static final String SELECT_BY_CLIENT_SKU_ID_AND_ID="select pp from ProductPojo pp where clientSkuId=:clientSkuId and clientId=:clientId";
    private static final String SELECT_BY_CLIENT_SKU_ID="select pp from ProductPojo pp where clientSkuId=:clientSkuId";
    @PersistenceContext
    private EntityManager entityManager;
    private TypedQuery<ProductPojo> getQuery(String jpql) {return entityManager.createQuery(jpql, ProductPojo.class);}
    public List<ProductPojo> getAll() {
        TypedQuery<ProductPojo> query=getQuery(SELECT_ALL);
        return query.getResultList();
    }
    @Transactional
    public void insert(ProductPojo productPojo) {
        entityManager.persist(productPojo);
    }

    public ProductPojo check(String clientSkuId, long clientId) {
        TypedQuery<ProductPojo> query=getQuery(SELECT_BY_CLIENT_SKU_ID_AND_ID);
        query.setParameter("clientSkuId",clientSkuId);
        query.setParameter("clientId",clientId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    //todo remove
    public ProductPojo check(String clientSkuId){
        TypedQuery<ProductPojo> query=getQuery(SELECT_BY_CLIENT_SKU_ID);
        query.setParameter("clientSkuId",clientSkuId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public ProductPojo getGlobal(Long globalSkuId) {
        TypedQuery<ProductPojo> query=getQuery(SELECT_BY_GID);
        query.setParameter("globalSkuId",globalSkuId);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
