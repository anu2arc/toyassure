package com.increff.channel.Util;

import com.increff.channel.spring.ApiException;
import com.increff.commons.form.ItemList;
import com.increff.commons.form.OrderForm;

import java.text.DecimalFormat;
import java.util.Objects;

public class OrderUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
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
