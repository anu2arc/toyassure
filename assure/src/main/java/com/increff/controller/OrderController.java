package com.increff.controller;


import com.increff.dto.OrderDto;
import com.increff.form.ChannelOrderUploadForm;
import com.increff.model.forms.OrderForm;
import com.increff.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderDto orderDto;
    @ApiOperation("place order")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(@RequestBody OrderForm orderForm) throws ApiException {
        orderDto.add(orderForm);
    }
    @ApiOperation("allocate order")
    @RequestMapping(value = "/allocate",method = RequestMethod.POST)
    public void allocate(@RequestParam long orderId) throws ApiException {
        orderDto.allocate(orderId);
    }
    @ApiOperation("generate invoice")
    @RequestMapping(value = "/invoice",method = RequestMethod.POST)
    public void generateInvoice(@RequestParam long orderId) throws ApiException {
        orderDto.generateInvoice(orderId);
    }

    @ApiOperation("place order thorough channel")
    @RequestMapping(value = "/channel",method = RequestMethod.POST)
    public String channelOrder(@RequestBody ChannelOrderUploadForm channelOrderUploadForm) throws ApiException {
        return orderDto.addChannelOrder(channelOrderUploadForm);
    }
}
