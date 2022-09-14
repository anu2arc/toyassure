package com.increff.controller;


import com.increff.dto.OrderDto;
import com.increff.model.form.OrderForm;
import com.increff.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderDto orderDto;
    @ApiOperation("")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(@RequestBody OrderForm orderForm) throws ApiException {
        orderDto.add(orderForm);
    }
}
