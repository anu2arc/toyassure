package com.increff.assure.controller;

import com.increff.assure.dto.ProductDto;
import com.increff.assure.model.data.ProductData;
import com.increff.assure.model.forms.ProductForm;
import com.increff.assure.model.forms.ProductUpdateForm;
import com.increff.assure.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Fetch all products")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<ProductData> getAll(){
        return productDto.getAll();
    }

    @ApiOperation(value = "add list of product")
    @RequestMapping(value = "/{clientId}",method = RequestMethod.POST)
    public void add(@PathVariable long clientId, @RequestBody List<ProductForm> productFormList) throws ApiException {
        productDto.add(clientId,productFormList);
    }

    @ApiOperation(value = "update product detail's")
    @RequestMapping(value = "/{globalSkuId}",method = RequestMethod.PUT)
    public void update(@PathVariable Long globalSkuId, @RequestBody ProductUpdateForm productUpdateForm) throws ApiException {
        productDto.update(globalSkuId,productUpdateForm);
    }
}
