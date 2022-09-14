package com.increff.controller;

import com.increff.dto.ChannelListingDto;
import com.increff.model.data.ChannelListingData;
import com.increff.model.form.ChannelListingUploadForm;
import com.increff.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
