package com.increff.service;

import com.increff.dao.ChannelDao;
import com.increff.pojo.ChannelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ChannelPojo channelPojo) throws ApiException {
        ChannelPojo pojo= channelDao.check(channelPojo.getName());
        if(pojo!=null) {
            throw new ApiException("Channel already exist");
        }
        channelDao.add(channelPojo);
    }

    public ChannelPojo getByName(String channelName) throws ApiException {
        ChannelPojo pojo= channelDao.check(channelName);
        if(pojo==null) {
            throw new ApiException("Invalid channel name");
        }
        return pojo;
    }
}
