package com.increff.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api/channel/")
public class OrderController {

    @ApiOperation("")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(){

    }
}
