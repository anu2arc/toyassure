package com.increff.assure.controller;

import com.increff.assure.dto.ChannelListingDto;
import com.increff.assure.model.data.ChannelListingData;
import com.increff.assure.model.forms.ChannelListingUploadForm;
import com.increff.assure.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/channel-listing")
public class ChannelListingController {
    @Autowired
    private ChannelListingDto channelListingDto;

    @ApiOperation("")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void add(@RequestBody ChannelListingUploadForm channelListingUploadForm) throws ApiException {
        channelListingDto.add(channelListingUploadForm);
    }

    @ApiOperation("")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<ChannelListingData> getAll() throws ApiException {
        return channelListingDto.getAll();
    }
}
