package com.increff.dto;

import com.increff.model.data.ChannelListingData;
import com.increff.model.enums.ClientType;
import com.increff.model.form.ChannelListingForm;
import com.increff.model.form.ChannelListingUploadForm;
import com.increff.pojo.ChannelListingPojo;
import com.increff.pojo.ChannelPojo;
import com.increff.pojo.ClientPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.ChannelListingUtil.normalize;
import static com.increff.Util.ChannelListingUtil.validate;
import static com.increff.dto.DtoHelper.convert;

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
        ClientPojo clientPojo=clientService.getByName(channelListingUploadForm.getClientName(), ClientType.CLIENT );
        ChannelPojo channelPojo=channelService.getByName(channelListingUploadForm.getChannelName());
        List<ChannelListingPojo> channelListingPojos=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(ChannelListingForm channelListingForm: channelListingUploadForm.getChannelList()){
            normalize(channelListingForm);
            ProductPojo productPojo= productService.check(channelListingForm.getClientSkuId(), clientPojo.getId());
            if(productPojo==null)
                error.append("Invalid clientId");
            else {
                channelListingPojos.add(convert(channelPojo.getId(),channelListingForm.getChannelSkuId(),clientPojo.getId(),productPojo.getGlobalSkuId()));
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        channelListingService.add(channelListingPojos);
    }
    public List<ChannelListingData> getAll() {
        return convert(channelListingService.getAll());
    }
}
