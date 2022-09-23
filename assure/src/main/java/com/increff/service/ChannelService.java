package com.increff.service;

import com.increff.dao.ChannelDao;
import com.increff.model.enums.InvoiceType;
import com.increff.pojo.ChannelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
            throw new ApiException("channel does not exists by given name");
        }
        return pojo;
    }
    private ChannelPojo internalPojo(){
        ChannelPojo channelPojo=new ChannelPojo();
        channelPojo.setName("INTERNAL");
        channelPojo.setInvoiceType(InvoiceType.SELF);
        return channelPojo;
    }

    public ChannelPojo get(long channelId) throws ApiException {
        ChannelPojo pojo=channelDao.getByID(channelId);
        if(pojo==null) {
            throw new ApiException("channel ID does not exists");
        }
        return pojo;
    }

    public List<ChannelPojo> getAll() {
        setInternal();
        return channelDao.getAll();
    }
}
