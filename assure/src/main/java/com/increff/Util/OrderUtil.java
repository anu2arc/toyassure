package com.increff.Util;

import com.increff.model.form.OrderForm;
import com.increff.model.form.OrderItemForm;
import com.increff.service.ApiException;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.Objects;

@Repository
public class OrderUtil {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public static void validate(OrderItemForm orderItemForm) throws ApiException {
        if(Objects.isNull(orderItemForm.getClientSkuId()) || orderItemForm.getClientSkuId().trim().equals(""))
            throw new ApiException("Invalid clientSkuId");
        if(orderItemForm.getOrderedQuantity()<0)
            throw new ApiException("Order quantity cannot be negative");
        if(orderItemForm.getOrderedQuantity()==0)
            throw new ApiException("Order quantity cannot be Zero");
        if(orderItemForm.getSellingPricePerUnit()<0)
            throw new ApiException("Selling price cannot be negative");
        if(orderItemForm.getSellingPricePerUnit()==0)
            throw new ApiException("Selling price cannot be zero");
        normalize(orderItemForm);
    }

    public static void validate(OrderForm orderForm) throws ApiException {
        if(Objects.isNull(orderForm.getClientName())||orderForm.getClientName().trim().equals(""))
            throw new ApiException("Client name cannot be null");
        if(Objects.isNull(orderForm.getCustomerName())||orderForm.getCustomerName().trim().equals(""))
            throw new ApiException("Customer name cannot be null");
        if(Objects.isNull(orderForm.getChannelOrderId())||orderForm.getChannelOrderId().trim().equals(""))
            throw new ApiException("Channel order id cannot be null");
        normalize(orderForm);
    }
    public static void normalize(OrderItemForm orderItemForm){
        orderItemForm.setClientSkuId(orderItemForm.getClientSkuId().trim().toLowerCase());
        orderItemForm.setSellingPricePerUnit(Double.valueOf(DECIMAL_FORMAT.format(orderItemForm.getSellingPricePerUnit())));
    }

    public static void normalize(OrderForm orderForm){
        orderForm.setClientName(orderForm.getClientName().trim().toLowerCase());
        orderForm.setChannelOrderId(orderForm.getChannelOrderId().trim().toLowerCase());
        orderForm.setCustomerName(orderForm.getCustomerName().trim().toLowerCase());
    }
}
