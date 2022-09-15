package com.increff.dto;

import com.increff.form.ChannelOrderUploadForm;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDto {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ChannelListingService channelListingService;
    @Autowired
    private OrderItemService orderItemService;


    public void add(ChannelOrderUploadForm orderUploadForm) {
    }
}
