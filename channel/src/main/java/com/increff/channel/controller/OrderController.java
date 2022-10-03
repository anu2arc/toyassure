package com.increff.channel.controller;

import com.increff.channel.dto.OrderDto;
import com.increff.commons.form.ChannelOrderUploadForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api/channel/")
public class OrderController {

    @Autowired
    private OrderDto orderDto;
    @ApiOperation("Create Order")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(@RequestBody ChannelOrderUploadForm orderUploadForm) throws Exception {
        orderDto.add(orderUploadForm);
    }

    @ApiOperation("generate invoice")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public void generateInvoice(){
        orderDto.generateInvoice();
    }
}
