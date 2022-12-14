package com.increff.assure.Util;

import com.increff.assure.model.forms.ChannelListingForm;
import com.increff.assure.model.forms.ChannelListingUploadForm;
import com.increff.assure.service.ApiException;
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

    public static void validate(ChannelListingForm channelListingForm) throws ApiException {
        if(Objects.isNull(channelListingForm.getChannelSkuId()) || channelListingForm.getChannelSkuId().trim().equals(""))
            throw new ApiException("Channel SkuId cannot be null");
        if(Objects.isNull(channelListingForm.getClientSkuId()) || channelListingForm.getClientSkuId().trim().equals(""))
            throw new ApiException("Client SkuId cannot be null");
        normalize(channelListingForm);
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
