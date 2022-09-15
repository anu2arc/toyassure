package com.increff.dao;

import com.increff.model.enums.ClientType;
import com.increff.pojo.ClientPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ClientDao {

    private static final String SELECT_ALL="select cp from ClientPojo cp";
    private static final String CHECK_PAIR="select cp from ClientPojo cp where name=:name and clientType=:type";
    private static final String SELECT_BY_ID="select cp from ClientPojo cp where id=:id";
    private static final String SELECT_BY_ID_AND_TYPE="select cp from ClientPojo cp where id=:id and clientType=:type";

    @PersistenceContext
    private EntityManager entityManager;

    private TypedQuery<ClientPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, ClientPojo.class);
    }

    public ClientPojo getByName(String clientName, ClientType type) {
        TypedQuery<ClientPojo> query=getQuery(CHECK_PAIR);
        query.setParameter("name",clientName);
        query.setParameter("type",type);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<ClientPojo> getAll() {
        TypedQuery<ClientPojo> query=getQuery(SELECT_ALL);
        return query.getResultList();
    }
    @Transactional
    public void add(ClientPojo userPojo) {
        entityManager.persist(userPojo);
    }

    public ClientPojo checkPair(ClientPojo userPojo) {
        TypedQuery<ClientPojo> query=getQuery(CHECK_PAIR);
        query.setParameter("name",userPojo.getName());
        query.setParameter("type",userPojo.getClientType());
        return query.getResultList().stream().findFirst().orElse(null);
    }
    //todo:: check for single result and em find
    public ClientPojo get(long id) {
//        entityManager.find(ClientPojo.class,id);
        TypedQuery<ClientPojo> query=getQuery(SELECT_BY_ID);
        query.setParameter("id",id);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public ClientPojo checkIdAndType(long clientId, ClientType type) {
        TypedQuery<ClientPojo> query=getQuery(SELECT_BY_ID_AND_TYPE);
        query.setParameter("id",clientId);
        query.setParameter("type",type);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
