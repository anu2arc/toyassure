package com.increff.service;

import com.increff.dao.ChannelListingDao;
import com.increff.pojo.BinSkuPojo;
import com.increff.pojo.ChannelListingPojo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChannelListingService {
    @Autowired
    private ChannelListingDao channelListingDao;

    @Transactional
    public void add(List<ChannelListingPojo> channelListingPojos) {
        for(ChannelListingPojo channelListingPojo:channelListingPojos){
            channelListingDao.add(channelListingPojo);
        }
    }

    public List<ChannelListingPojo> getAll() {
        return channelListingDao.getAll();
    }
}
