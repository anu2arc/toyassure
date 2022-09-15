package com.increff.controller;

import com.increff.dto.ClientDto;
import com.increff.model.data.ClientData;
import com.increff.model.forms.ClientForm;
import com.increff.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/user")
public class ClientController {
    @Autowired
    private ClientDto clientDto;
    @ApiOperation(value="Add a new user")
    @RequestMapping(path = "",method = RequestMethod.POST)
    public void add(@RequestBody ClientForm clientForm) throws ApiException {
        clientDto.add(clientForm);
    }
    @ApiOperation(value="Fetch all user details")
    @RequestMapping(path = "",method = RequestMethod.GET)
    public List<ClientData> getAll(){
        return clientDto.getAll();
    }

    @ApiOperation(value="Fetch a user details by Id")
    @RequestMapping(path = "/{clientId}",method = RequestMethod.GET)
    public ClientData get(@PathVariable long clientId) throws ApiException {
        return clientDto.get(clientId);
    }
}
