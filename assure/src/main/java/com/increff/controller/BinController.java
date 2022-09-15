package com.increff.controller;

import com.increff.dto.BinDto;
import com.increff.model.data.BinData;
import com.increff.model.data.BinSkuData;
import com.increff.model.forms.BinSkuForm;
import com.increff.model.forms.BinSkuUpdateForm;
import com.increff.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/bin")
public class BinController {
    @Autowired
    private BinDto binDto;

    @ApiOperation("Fetch all bin id's")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<BinData> getAll(){
        return binDto.getAll();
    }

    @ApiOperation("Create N no of bins")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void create(@RequestParam int noOfBin){
        binDto.create(noOfBin);
    }

    @ApiOperation("Upload bin wise inventory")
    @RequestMapping(value = "/upload/{clientId}",method = RequestMethod.POST)
    public void add(@RequestParam long clientId,@RequestBody List<BinSkuForm> binSkuForms) throws ApiException {
        binDto.add(binSkuForms,clientId);
    }

    @ApiOperation("Fetch All bin with Sku's")
    @RequestMapping(value = "/sku",method = RequestMethod.GET)
    public List<BinSkuData> getBinSku(){
        return binDto.getAllSku();
    }

    @ApiOperation("Edit by id")
    @RequestMapping(value = "/sku/{id}",method = RequestMethod.PUT)
    public void update(@RequestParam long id, @RequestBody BinSkuUpdateForm binSkuUpdateForm) throws ApiException {
        binDto.update(id,binSkuUpdateForm);
    }
}
