package com.increff.Util;

import com.increff.model.forms.ChannelForm;
import com.increff.service.ApiException;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Objects;

@Repository
public class ChannelUtil {
    public static void validate(ChannelForm channelForm) throws ApiException {
        if(Objects.isNull(channelForm.getName()) || channelForm.getName().trim().equals(""))
            throw new ApiException("Channel name cannot be null");
        if(Objects.isNull(channelForm.getInvoiceType()) || channelForm.getInvoiceType().trim().equals(""))
            throw new ApiException("Invoice type cannot be empty");
        normalize(channelForm);
        if(!channelForm.getInvoiceType().equals("SELF") && !channelForm.getInvoiceType().equals("CHANNEL"))
            throw new ApiException("Invalid invoice type");
    }
    public static void normalize(ChannelForm channelForm){
        channelForm.setName(channelForm.getName().trim().toLowerCase(Locale.ROOT));
        channelForm.setInvoiceType(channelForm.getInvoiceType().trim().toUpperCase(Locale.ROOT));
    }
}
