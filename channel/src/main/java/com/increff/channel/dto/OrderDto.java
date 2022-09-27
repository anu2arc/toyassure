package com.increff.channel.dto;

import com.increff.commons.form.ChannelOrderUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDto {

    @Autowired
    private AssureClient assureClient;

    public String add(ChannelOrderUploadForm channelOrderUploadForm) throws Exception {
        return assureClient.post("/order/channel",channelOrderUploadForm);
    }

}
