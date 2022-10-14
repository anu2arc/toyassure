package com.increff.assure.Util;

import com.increff.assure.model.forms.OrderItemForm;
import com.increff.assure.service.ApiException;
import com.increff.commons.form.ItemList;
import com.increff.commons.form.OrderForm;
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

    public static void validate(com.increff.assure.model.forms.OrderForm orderForm) throws ApiException {
        if(Objects.isNull(orderForm.getClientName())||orderForm.getClientName().trim().equals(""))
            throw new ApiException("Client name cannot be null");
        if(Objects.isNull(orderForm.getCustomerName())||orderForm.getCustomerName().trim().equals(""))
            throw new ApiException("Customer name cannot be null");
        if(Objects.isNull(orderForm.getChannelOrderId())||orderForm.getChannelOrderId().trim().equals(""))
            throw new ApiException("Channel order id cannot be null");
        if(orderForm.getOrderItems().size()==0)
            throw new ApiException("There must be at least one order item");
        normalize(orderForm);
    }
    public static void normalize(OrderItemForm orderItemForm){
        orderItemForm.setClientSkuId(orderItemForm.getClientSkuId().trim().toLowerCase());
        orderItemForm.setSellingPricePerUnit(Double.valueOf(DECIMAL_FORMAT.format(orderItemForm.getSellingPricePerUnit())));
    }

    public static void normalize(com.increff.assure.model.forms.OrderForm orderForm){
        orderForm.setClientName(orderForm.getClientName().trim().toLowerCase());
        orderForm.setChannelOrderId(orderForm.getChannelOrderId().trim().toLowerCase());
        orderForm.setCustomerName(orderForm.getCustomerName().trim().toLowerCase());
    }

    public static void validate(OrderForm orderUploadForm) throws ApiException {
        if(Objects.isNull(orderUploadForm.getChannelName()) || orderUploadForm.getChannelName().trim().equals(""))
            throw new ApiException("Channel name cannot be empty");
        if(Objects.isNull(orderUploadForm.getChannelOrderId()) || orderUploadForm.getChannelOrderId().trim().equals(""))
            throw new ApiException("Channel order id cannot be empty");
        if(orderUploadForm.getItems().size()==0)
            throw new ApiException("No order item found");
        normalize(orderUploadForm);
    }
    public static void validate(ItemList itemList) throws ApiException {
        if(Objects.isNull(itemList.getChannelSkuId()) || itemList.getChannelSkuId().trim().equals(""))
            throw new ApiException("Channel sku id cannot be empty");
        if(itemList.getQuantity()<0)
            throw new ApiException("Quantity cannot be negative");
        if(itemList.getQuantity()==0)
            throw new ApiException("Quantity cannot be zero");
        if(itemList.getSellingPrice()<0)
            throw new ApiException("selling price cannot be negative");
        if(itemList.getSellingPrice()==0)
            throw new ApiException("selling price cannot be zero");

    }

    public static void normalize(ItemList itemList){
        itemList.setChannelSkuId(itemList.getChannelSkuId().trim().toLowerCase());
        itemList.setSellingPrice(Double.valueOf(DECIMAL_FORMAT.format(itemList.getSellingPrice())));
    }
    public static void normalize(OrderForm orderUploadForm) {
        orderUploadForm.setChannelName(orderUploadForm.getChannelName().trim().toLowerCase());
        orderUploadForm.setChannelOrderId(orderUploadForm.getChannelOrderId().trim().toLowerCase());
    }
}
