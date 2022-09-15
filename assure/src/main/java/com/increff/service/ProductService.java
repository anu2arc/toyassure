package com.increff.service;

import com.increff.dao.ProductDao;
import com.increff.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.getAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void add(List<ProductPojo> productPojoList) throws ApiException {
        Set<String> skuIdSet =new HashSet<>();
        StringBuilder error=new StringBuilder();
        for(ProductPojo productPojo:productPojoList){
            try {
                //todo :: move this check to dto
                if (skuIdSet.contains(productPojo.getClientSkuId() + productPojo.getClientId()))
                    throw new ApiException("Duplicate entry for product :"+productPojo.getClientSkuId()+"\n");
                if(check(productPojo.getClientSkuId(),productPojo.getClientId())!=null)
                    throw new ApiException("Product already exist");
                skuIdSet.add(productPojo.getClientSkuId() + productPojo.getClientId());
            }
            catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        for(ProductPojo productPojo:productPojoList){
            productDao.insert(productPojo);
        }
    }
    public ProductPojo check(String clientSkuId) throws ApiException {
        ProductPojo productPojo=productDao.check(clientSkuId);
        if(productPojo==null)
            throw new ApiException("Invalid clientSkuId :"+clientSkuId);
        return productPojo;
    }
    public ProductPojo check(String clientSkuId,long clientID) throws ApiException {
        return productDao.check(clientSkuId,clientID);
    }
    @Transactional(rollbackOn = ApiException.class)
    public void update(ProductPojo productPojo) throws ApiException {
        //todo change names
        ProductPojo presentPojo=productDao.getGlobal(productPojo.getGlobalSkuId());
        if(presentPojo==null)
            throw new ApiException("Invalid Global Sku Id");
        setProductPojo(productPojo,presentPojo);
    }
    private void setProductPojo(ProductPojo productPojo, ProductPojo presentPojo){
        presentPojo.setDescription(productPojo.getDescription());
        presentPojo.setName(productPojo.getName());
        presentPojo.setMrp(productPojo.getMrp());
        presentPojo.setBrandId(productPojo.getBrandId());
    }
}
