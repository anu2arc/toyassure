package com.increff.assure.dto;

import com.increff.assure.model.data.ChannelListingData;
import com.increff.assure.model.forms.ChannelListingForm;
import com.increff.assure.model.forms.ChannelListingUploadForm;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.*;
import com.increff.commons.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.assure.Util.ChannelListingUtil.normalize;
import static com.increff.assure.Util.ChannelListingUtil.validate;
import static com.increff.assure.dto.DtoHelper.convertListingFormToPojo;
import static com.increff.assure.dto.DtoHelper.convertListingPojoToListingData;
//import static com.increff.assure.dto.DtoHelper.convertListingPojoToListingData;

@Service

public class ChannelListingDto {
    @Autowired
    private ChannelListingService channelListingService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProductService productService;

    public void add(ChannelListingUploadForm channelListingUploadForm) throws ApiException {
        validate(channelListingUploadForm);
        long clientId=userService.getByName(channelListingUploadForm.getClientName(), ClientType.CLIENT ).getId();
        long channelId=channelService.getByName(channelListingUploadForm.getChannelName()).getId();
        List<ChannelListingPojo> channelListingPojos= getPojoList(channelListingUploadForm.getChannelList(),clientId,channelId);
        channelListingService.add(channelListingPojos);
    }
    private List<ChannelListingPojo> getPojoList(List<ChannelListingForm> listingForms, long clientId, long channelId) throws ApiException {
        List<ChannelListingPojo> channelListingPojos=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(ChannelListingForm channelListingForm: listingForms){
            try{
                validate(channelListingForm);
                ProductPojo productPojo= productService.check(channelListingForm.getClientSkuId(), clientId);
                if(productPojo==null)
                    throw new ApiException("Product doesn't exist for given client SKU ID");
                channelListingPojos.add(convertListingFormToPojo(channelId,channelListingForm.getChannelSkuId(),clientId,productPojo.getGlobalSkuId()));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return channelListingPojos;
    }
    public List<ChannelListingData> getAll() throws ApiException {
        List<ChannelListingPojo> channelListingPojos=channelListingService.getAll();
        List<ChannelListingData> channelListingData=new ArrayList<>();
        for(ChannelListingPojo channelListingPojo:channelListingPojos){
            String channelName=channelService.get(channelListingPojo.getChannelId()).getName();
            String clientName= userService.get(channelListingPojo.getClientId()).getName();
            String clientSkuId= productService.get(channelListingPojo.getGlobalSkuId()).getClientSkuId();
            channelListingData.add(convertListingPojoToListingData(channelListingPojo,channelName,clientName,clientSkuId));
        }
        return channelListingData;
    }


}
