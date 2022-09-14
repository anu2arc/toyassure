package com.increff.service;

import com.increff.dao.ChannelDao;
import com.increff.model.enums.InvoiceType;
import com.increff.pojo.ChannelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChannelService {

    @Autowired
    private ChannelDao channelDao;

    private void setInternal(){
        ChannelPojo pojo=channelDao.check("INTERNAL");
        if(pojo==null){
            ChannelPojo channelPojo=internalPojo();
            channelDao.add(channelPojo);
        }
    }
    @Transactional(rollbackOn = ApiException.class)
    public void add(ChannelPojo channelPojo) throws ApiException {
        setInternal();
        ChannelPojo pojo= channelDao.check(channelPojo.getName());
        if(pojo!=null) {
            throw new ApiException("Channel already exist");
        }
        channelDao.add(channelPojo);
    }
    public ChannelPojo getByName(String channelName) throws ApiException {
        setInternal();
        ChannelPojo pojo=channelDao.check(channelName);
        if(pojo==null) {
            throw new ApiException("Invalid channel name");
        }
        return pojo;
    }
    private ChannelPojo internalPojo(){
        ChannelPojo channelPojo=new ChannelPojo();
        channelPojo.setName("INTERNAL");
        channelPojo.setInvoiceType(InvoiceType.SELF);
        return channelPojo;
    }
}
