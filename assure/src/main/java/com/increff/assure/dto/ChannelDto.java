package com.increff.assure.dto;

import com.increff.assure.model.data.ChannelData;
import com.increff.assure.model.forms.ChannelForm;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.increff.assure.Util.ChannelUtil.validate;
import static com.increff.assure.dto.DtoHelper.convertChannelFormToPojo;
import static com.increff.assure.dto.DtoHelper.convertChannelPojoToDataList;

@Service
public class ChannelDto {

    @Autowired
    private ChannelService channelService;
    public void add(ChannelForm channelForm) throws ApiException {
        validate(channelForm);
        channelService.add(convertChannelFormToPojo(channelForm));
    }

    public List<ChannelData> getAll() {
        return convertChannelPojoToDataList(channelService.getAll());
    }
}
