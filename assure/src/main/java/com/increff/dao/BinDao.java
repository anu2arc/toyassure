package com.increff.dao;

import com.increff.pojo.BinPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BinDao {
    private static final String SELECT_ALL="select bp from BinPojo bp";
    private static final String SELECT_BY_BID="select bp from BinPojo bp where binId=:binId";
    @PersistenceContext
    private EntityManager entityManager;
    private TypedQuery<BinPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, BinPojo.class);
    }

    public List<BinPojo> getAll() {
        TypedQuery<BinPojo> query=getQuery(SELECT_ALL);
        return query.getResultList();
    }
    @Transactional
    public void insert(BinPojo binPojo) {
        entityManager.persist(binPojo);
    }

    public BinPojo check(long binId) {
        TypedQuery<BinPojo> query=getQuery(SELECT_BY_BID);
        query.setParameter("binId",binId);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
