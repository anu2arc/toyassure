package com.increff.controller;

import com.increff.dto.BinDto;
import com.increff.model.data.BinData;
import com.increff.model.data.BinSkuData;
import com.increff.model.form.BinSkuForm;
import com.increff.model.form.BinSkuUpdateForm;
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
    @RequestMapping(value = "/Upload/{clientId}",method = RequestMethod.POST)
    public void add(@RequestBody List<BinSkuForm> binSkuForms,@RequestParam long clientId) throws ApiException {
        binDto.add(binSkuForms,clientId);
    }

    @ApiOperation("Fetch All bin with Sku's")
    @RequestMapping(value = "/Sku",method = RequestMethod.GET)
    public List<BinSkuData> getBinSku(){
        return binDto.getAllSku();
    }

    @ApiOperation("Edit by id")
    @RequestMapping(value = "/Sku/{id}",method = RequestMethod.PATCH)
    public void update(@RequestParam long id, @RequestBody BinSkuUpdateForm binSkuUpdateForm) throws ApiException {
        binDto.update(id,binSkuUpdateForm);
    }
}
