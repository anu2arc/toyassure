package com.increff.assure.dao;

import com.increff.assure.pojo.UserPojo;
import com.increff.commons.enums.ClientType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDao {

    private static final String SELECT_ALL="select up from UserPojo up";
    private static final String CHECK_PAIR="select up from UserPojo up where name=:name and clientType=:type";
    private static final String SELECT_BY_ID="select up from UserPojo up where id=:id";
    private static final String SELECT_BY_ID_AND_TYPE="select up from UserPojo up where id=:id and clientType=:type";

    @PersistenceContext
    private EntityManager entityManager;

    private TypedQuery<UserPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, UserPojo.class);
    }

    public UserPojo getByName(String clientName, ClientType type) {
        TypedQuery<UserPojo> query=getQuery(CHECK_PAIR);
        query.setParameter("name",clientName);
        query.setParameter("type",type);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<UserPojo> getAll() {
        TypedQuery<UserPojo> query=getQuery(SELECT_ALL);
        return query.getResultList();
    }
    @Transactional
    public void add(UserPojo userPojo) {
        entityManager.persist(userPojo);
    }

    public UserPojo checkPair(UserPojo userPojo) {
        TypedQuery<UserPojo> query=getQuery(CHECK_PAIR);
        query.setParameter("name",userPojo.getName());
        query.setParameter("type",userPojo.getClientType());
        return query.getResultList().stream().findFirst().orElse(null);
    }
    //todo:: check for single result and em find
    public UserPojo get(long id) {
//        entityManager.find(ClientPojo.class,id);
        TypedQuery<UserPojo> query=getQuery(SELECT_BY_ID);
        query.setParameter("id",id);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public UserPojo checkIdAndType(long clientId, ClientType type) {
        TypedQuery<UserPojo> query=getQuery(SELECT_BY_ID_AND_TYPE);
        query.setParameter("id",clientId);
        query.setParameter("type",type);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
