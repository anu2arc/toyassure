package com.increff.dto;

import com.increff.form.ChannelOrderUploadForm;
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
