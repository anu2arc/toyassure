package com.increff.channel.controller;

import com.increff.channel.dto.OrderDto;
import com.increff.commons.data.InvoiceData;
import com.increff.commons.data.OrderData;
import com.increff.commons.form.ChannelOrderUploadForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/channel")
public class OrderController {

    @Autowired
    private OrderDto orderDto;
    @ApiOperation("Create Order")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(@RequestBody ChannelOrderUploadForm orderUploadForm) throws Exception {
        orderDto.add(orderUploadForm);
    }
    @ApiOperation("generate invoice")
    @RequestMapping(value = "/invoice/{orderId}",method = RequestMethod.POST)
    public String generateInvoice(@RequestBody InvoiceData invoiceData) throws IOException, TransformerException {
        return orderDto.generateInvoice(invoiceData);
    }

    @ApiOperation("Create Order")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<OrderData> getAll() throws Exception {
        return orderDto.getAll();
    }
}
