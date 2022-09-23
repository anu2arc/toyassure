package com.increff.dao;

import com.increff.pojo.ChannelPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ChannelDao {
    private static final String SELECT_BY_NAME="select cp from ChannelPojo cp where name=:name";
    private static final String SELECT_BY_ID="select cp from ChannelPojo cp where id=:id";
    private static final String SELECT_ALL="select cp from ChannelPojo cp";



    private TypedQuery<ChannelPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, ChannelPojo.class);
    }

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void add(ChannelPojo channelPojo) {
        entityManager.persist(channelPojo);
    }

    public ChannelPojo check(String name){
        TypedQuery<ChannelPojo> query=getQuery(SELECT_BY_NAME);
        query.setParameter("name",name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public ChannelPojo getByID(long channelId) {
        TypedQuery<ChannelPojo> query=getQuery(SELECT_BY_ID);
        query.setParameter("id",channelId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<ChannelPojo> getAll() {
        TypedQuery<ChannelPojo> query=getQuery(SELECT_ALL);
        return query.getResultList();
    }
}
