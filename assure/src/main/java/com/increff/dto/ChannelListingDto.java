package com.increff.dto;

import com.increff.model.data.ChannelListingData;
import com.increff.model.enums.ClientType;
import com.increff.model.forms.ChannelListingForm;
import com.increff.model.forms.ChannelListingUploadForm;
import com.increff.pojo.ChannelListingPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.ChannelListingUtil.normalize;
import static com.increff.Util.ChannelListingUtil.validate;
import static com.increff.dto.DtoHelper.*;

@Repository
public class ChannelListingDto {
    @Autowired
    private ChannelListingService channelListingService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProductService productService;

    public void add(ChannelListingUploadForm channelListingUploadForm) throws ApiException {
        validate(channelListingUploadForm);
        long clientId=clientService.getByName(channelListingUploadForm.getClientName(), ClientType.CLIENT ).getId();
        long channelId=channelService.getByName(channelListingUploadForm.getChannelName()).getId();
        List<ChannelListingPojo> channelListingPojos= getPojoList(channelListingUploadForm.getChannelList(),clientId,channelId);
        channelListingService.add(channelListingPojos);
    }
    private List<ChannelListingPojo> getPojoList(List<ChannelListingForm> listingForms, long clientId, long channelId) throws ApiException {
        List<ChannelListingPojo> channelListingPojos=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(ChannelListingForm channelListingForm: listingForms){
            normalize(channelListingForm);
            ProductPojo productPojo= productService.check(channelListingForm.getClientSkuId(), clientId);
            if(productPojo==null)
                error.append("Invalid clientId");
            else {
                channelListingPojos.add(convertListingFormToPojo(channelId,channelListingForm.getChannelSkuId(),clientId,productPojo.getGlobalSkuId()));
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return channelListingPojos;
    }
    public List<ChannelListingData> getAll() {
        return convertListingPojoToListingData(channelListingService.getAll());
    }
}
