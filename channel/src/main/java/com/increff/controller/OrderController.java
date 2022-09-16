package com.increff.controller;

import com.increff.dto.OrderDto;
import com.increff.form.ChannelOrderUploadForm;
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
}
