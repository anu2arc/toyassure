package com.increff.controller;

import com.increff.dto.ChannelDto;
import com.increff.model.form.ChannelForm;
import com.increff.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
