package com.increff.dto;

import com.increff.model.data.ChannelData;
import com.increff.model.forms.ChannelForm;
import com.increff.pojo.ChannelPojo;
import com.increff.service.ApiException;
import com.increff.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.increff.Util.ChannelUtil.validate;
import static com.increff.dto.DtoHelper.convertChannelFormToPojo;
import static com.increff.dto.DtoHelper.convertChannelPojoToDataList;

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
