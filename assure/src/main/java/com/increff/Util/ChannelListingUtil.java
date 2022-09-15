package com.increff.Util;

import com.increff.model.forms.ChannelListingForm;
import com.increff.model.forms.ChannelListingUploadForm;
import com.increff.service.ApiException;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ChannelListingUtil {
    public static void validate(ChannelListingUploadForm channelListingUploadForm) throws ApiException {
        if(Objects.isNull(channelListingUploadForm.getChannelName()) || channelListingUploadForm.getChannelName().trim().equals(""))
            throw new ApiException("Channel name cannot be null");
        if(Objects.isNull(channelListingUploadForm.getClientName()) || channelListingUploadForm.getClientName().trim().equals(""))
            throw new ApiException("Client name cannot be null");
        normalize(channelListingUploadForm);
    }
    public static void normalize(ChannelListingUploadForm channelListingUploadForm){
        channelListingUploadForm.setChannelName(channelListingUploadForm.getChannelName().trim().toLowerCase());
        channelListingUploadForm.setClientName(channelListingUploadForm.getClientName().trim().toLowerCase());
    }
    public static void normalize(ChannelListingForm channelListingForm){
        channelListingForm.setChannelSkuId(channelListingForm.getChannelSkuId().trim().toLowerCase());
        channelListingForm.setClientSkuId(channelListingForm.getClientSkuId().trim().toLowerCase());
    }
}
