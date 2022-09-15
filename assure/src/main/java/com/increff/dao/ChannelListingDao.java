package com.increff.dao;

import com.increff.pojo.ChannelListingPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ChannelListingDao {

    private static final String SELECT_ALL="select cp from ChannelListingPojo cp";
    private static final String SELECT_GSKU="select cp from ChannelListingPojo cp where channelId=:channelId and channelSkuId=:channelSkuId and clientId=:clientId";

    private TypedQuery<ChannelListingPojo> getQuery(String jpql) {
        return entityManager.createQuery(jpql, ChannelListingPojo.class);
    }
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void add(ChannelListingPojo channelListingPojo) {
        entityManager.persist(channelListingPojo);
    }

    public List<ChannelListingPojo> getAll() {
        TypedQuery<ChannelListingPojo> query=getQuery(SELECT_ALL);
        return query.getResultList();
    }
    public ChannelListingPojo get(String channelSkuId, Long clientId, long channelId) {
        TypedQuery<ChannelListingPojo> query=getQuery(SELECT_GSKU);
        query.setParameter("channelId",channelId);
        query.setParameter("channelSkuId",channelSkuId);
        query.setParameter("clientId",channelId);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
