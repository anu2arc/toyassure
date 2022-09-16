package com.increff.dto;

import com.increff.model.data.ProductData;
import com.increff.model.enums.ClientType;
import com.increff.model.forms.ProductForm;
import com.increff.model.forms.ProductUpdateForm;
import com.increff.pojo.ProductPojo;
import com.increff.service.ApiException;
import com.increff.service.ClientService;
import com.increff.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.ProductUtil.validate;
import static com.increff.dto.DtoHelper.*;

@Repository
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    public List<ProductData> getAll() {
        return convertProductPojoToProductDataList(productService.getAll());
    }

    public void add(long clientId, List<ProductForm> productFormList) throws ApiException {
        clientService.checkIdAndType(clientId, ClientType.CLIENT);
        List<ProductPojo> productPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        //todo do dubplicate validation here only with new function
        for(ProductForm productForm:productFormList){
            try{
                validate(productForm);
                productPojoList.add((convertProductFormToProductPojo(productForm,clientId)));
            }
            catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        productService.add(productPojoList);
    }

    public void update(long globalId, ProductUpdateForm productUpdateForm) throws ApiException {
        validate(productUpdateForm);
        productService.update(convertProductFormToPojo(productUpdateForm,globalId));
    }

}
