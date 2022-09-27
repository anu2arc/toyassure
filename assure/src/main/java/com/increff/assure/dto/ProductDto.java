package com.increff.assure.dto;

import com.increff.assure.model.data.ProductData;
import com.increff.assure.model.forms.ProductForm;
import com.increff.assure.model.forms.ProductUpdateForm;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.ClientService;
import com.increff.assure.service.ProductService;
import com.increff.commons.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.assure.Util.ProductUtil.validate;

@Service
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;

    private Set<String> skuIdSet =new HashSet<>();
    public List<ProductData> getAll() {
        return DtoHelper.convertProductPojoToProductDataList(productService.getAll());
    }

    public void add(long clientId, List<ProductForm> productFormList) throws ApiException {
        clientService.checkIdAndType(clientId, ClientType.CLIENT);
        List<ProductPojo> productPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(ProductForm productForm:productFormList){
            try{
                validate(productForm);
                duplicateCheck(productForm.getClientSkuId(),clientId);
                productPojoList.add((DtoHelper.convertProductFormToProductPojo(productForm,clientId)));
            }
            catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        productService.add(productPojoList);
    }
    private void duplicateCheck(String clientSkuId,Long clientId) throws ApiException {
        if(skuIdSet.contains(clientSkuId))
            throw new ApiException("Duplicate entry for product :"+clientSkuId);
        if(productService.check(clientSkuId,clientId)!=null)
            throw new ApiException("Product already exist");
        skuIdSet.add(clientSkuId);
    }
    public void update(long globalId, ProductUpdateForm productUpdateForm) throws ApiException {
        validate(productUpdateForm);
        productService.update(DtoHelper.convertProductFormToPojo(productUpdateForm,globalId));
    }

}
