package com.increff.assure.controller;

import com.increff.assure.dto.ChannelDto;
import com.increff.assure.model.data.ChannelData;
import com.increff.assure.model.forms.ChannelForm;
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
@RequestMapping("/api/channel")
public class ChannelController {
    @Autowired
    private ChannelDto channelDto;

    @ApiOperation("")
    @RequestMapping(value="",method = RequestMethod.POST)
    public void add(@RequestBody ChannelForm channelForm) throws ApiException {
        channelDto.add(channelForm);
    }

    @ApiOperation("")
    @RequestMapping(value="",method = RequestMethod.GET)
    public List<ChannelData> getAll() throws ApiException {
        return channelDto.getAll();
    }
}
