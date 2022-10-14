package com.increff.assure.controller;

import com.increff.assure.dto.UserDto;
import com.increff.assure.model.data.UserData;
import com.increff.assure.model.forms.UserForm;
import com.increff.assure.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserDto userDto;
    @ApiOperation(value="Add a new user")
    @RequestMapping(path = "",method = RequestMethod.POST)
    public void add(@RequestBody UserForm userForm) throws ApiException {
        userDto.add(userForm);
    }
    @ApiOperation(value="Fetch all user details")
    @RequestMapping(path = "",method = RequestMethod.GET)
    public List<UserData> getAll(){
        return userDto.getAll();
    }

    @ApiOperation(value="Fetch a user details by Id")
    @RequestMapping(path = "/{clientId}",method = RequestMethod.GET)
    public UserData get(@PathVariable long clientId) throws ApiException {
        return userDto.get(clientId);
    }
}
