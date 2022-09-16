package com.increff.Util;

import com.increff.form.ChannelOrderForm;
import com.increff.form.ChannelOrderUploadForm;
import com.increff.spring.ApiException;

import java.text.DecimalFormat;
import java.util.Objects;

public class OrderUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    public static void validate(ChannelOrderUploadForm orderUploadForm) throws ApiException {
        if(Objects.isNull(orderUploadForm.getChannelName()) || orderUploadForm.getChannelName().trim().equals(""))
            throw new ApiException("Channel name cannot be empty");
        if(Objects.isNull(orderUploadForm.getChannelOrderId()) || orderUploadForm.getChannelOrderId().trim().equals(""))
            throw new ApiException("Channel order id cannot be empty");
        if(orderUploadForm.getItems().size()==0)
            throw new ApiException("No order item found");
        normalize(orderUploadForm);
    }
    public static void validate(ChannelOrderForm orderForm) throws ApiException {
        if(Objects.isNull(orderForm.getChannelSkuId()) || orderForm.getChannelSkuId().trim().equals(""))
            throw new ApiException("Channel sku id cannot be empty");
        if(orderForm.getQuantity()<0)
            throw new ApiException("Quantity cannot be negative");
        if(orderForm.getQuantity()==0)
            throw new ApiException("Quantity cannot be zero");
        if(orderForm.getSellingPrice()<0)
            throw new ApiException("selling price cannot be negative");
        if(orderForm.getSellingPrice()==0)
            throw new ApiException("selling price cannot be zero");

    }

    public static void normalize(ChannelOrderForm orderForm){
        orderForm.setChannelSkuId(orderForm.getChannelSkuId().trim().toLowerCase());
        orderForm.setSellingPrice(Double.valueOf(DECIMAL_FORMAT.format(orderForm.getSellingPrice())));
    }
    public static void normalize(ChannelOrderUploadForm orderUploadForm) {
        orderUploadForm.setChannelName(orderUploadForm.getChannelName().trim().toLowerCase());
        orderUploadForm.setChannelOrderId(orderUploadForm.getChannelOrderId().trim().toLowerCase());
    }
}
