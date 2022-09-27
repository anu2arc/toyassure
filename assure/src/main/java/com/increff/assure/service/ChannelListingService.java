package com.increff.assure.service;

import com.increff.assure.dao.ChannelListingDao;
import com.increff.assure.pojo.ChannelListingPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChannelListingService {
    @Autowired
    private ChannelListingDao channelListingDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<ChannelListingPojo> channelListingPojos) {
        for(ChannelListingPojo channelListingPojo:channelListingPojos){
            channelListingDao.add(channelListingPojo);
        }
    }

    public List<ChannelListingPojo> getAll() {
        return channelListingDao.getAll();
    }

    public long getGlobalSkuId(String channelSkuId, Long clientId, long channelId) throws ApiException {
        ChannelListingPojo channelListingPojo=channelListingDao.get(channelSkuId,clientId,channelId);
        if(channelListingPojo==null)
            throw new ApiException("No entry for given channel SKU ID");
        return channelListingPojo.getGlobalSkuId();
    }
}
