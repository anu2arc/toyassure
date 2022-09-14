package com.increff.dao;

import com.increff.pojo.BinSkuPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BinSkuDao {

    private static final String SELECT_BY_GID_AND_BID="select bp from BinSkuPojo bp where binId=:binId and globalSkuId=:globalSkuId";
    private static final String SELECT_ALL="select bp from BinSkuPojo bp";
    private static final String SELECT_BY_ID="select bp from BinSkuPojo bp where id=:id";

    @PersistenceContext
    private EntityManager entityManager;

    private TypedQuery<BinSkuPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, BinSkuPojo.class);
    }

    @Transactional
    public void add(BinSkuPojo binSkuPojo) {
        entityManager.persist(binSkuPojo);
    }

    public List<BinSkuPojo> getAllSku() {
        TypedQuery<BinSkuPojo> query= getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public BinSkuPojo check(long binId,long globalSkuId){
        TypedQuery<BinSkuPojo> query= getQuery(SELECT_BY_GID_AND_BID);
        query.setParameter("binId",binId);
        query.setParameter("globalSkuId",globalSkuId);
        return query.getResultList().stream().findFirst().orElse(null);
    }
    public BinSkuPojo get(long id) {
        TypedQuery<BinSkuPojo> query= getQuery(SELECT_BY_ID);
        query.setParameter("id",id);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
