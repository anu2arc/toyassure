package com.increff.dto;

import com.increff.model.form.ChannelForm;
import com.increff.service.ApiException;
import com.increff.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.increff.Util.ChannelUtil.validate;
import static com.increff.dto.DtoHelper.convert;

@Repository
public class ChannelDto {

    @Autowired
    private ChannelService channelService;
    public void add(ChannelForm channelForm) throws ApiException {
        validate(channelForm);
        channelService.add(convert(channelForm));
    }
}
