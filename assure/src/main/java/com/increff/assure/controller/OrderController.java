package com.increff.assure.controller;


import com.increff.assure.dto.OrderDto;
import com.increff.assure.service.ApiException;
import com.increff.commons.data.OrderData;
import com.increff.commons.form.OrderForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderDto orderDto;
    @ApiOperation("place order")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(@RequestBody com.increff.assure.model.forms.OrderForm orderForm) throws ApiException {
        orderDto.add(orderForm);
    }

    @ApiOperation("fetch all orders")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return orderDto.getAll();
    }

    @ApiOperation("allocate order")
    @RequestMapping(value = "/allocate/{orderId}",method = RequestMethod.POST)
    public void allocate(@PathVariable long orderId) throws ApiException {
        orderDto.allocate(orderId);
    }
    @ApiOperation("generate invoice")
    @RequestMapping(value = "/invoice/{orderId}",method = RequestMethod.POST)
    public String generateInvoice(@PathVariable long orderId) throws ApiException, IOException, TransformerException {
        return orderDto.generateInvoice(orderId);
    }

    @ApiOperation("place order thorough channel")
    @RequestMapping(value = "/channel",method = RequestMethod.POST)
    public String channelOrder(@RequestBody OrderForm channelOrderUploadForm) throws ApiException {
        return orderDto.addChannelOrder(channelOrderUploadForm);
    }
}
