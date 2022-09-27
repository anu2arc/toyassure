package com.increff.assure.service;

import com.increff.assure.dao.ProductDao;
import com.increff.assure.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        for(ProductPojo productPojo:productPojoList){
            productDao.insert(productPojo);
        }
    }
    public ProductPojo check(String clientSkuId) throws ApiException {
        ProductPojo productPojo=productDao.check(clientSkuId);
        if(productPojo==null)
            throw new ApiException("product does not exist for given id:"+clientSkuId);
        return productPojo;
    }
    public ProductPojo check(String clientSkuId,long clientID) throws ApiException {
        return productDao.check(clientSkuId,clientID);
    }
    @Transactional(rollbackOn = ApiException.class)
    public void update(ProductPojo productPojo) throws ApiException {
        ProductPojo pojo=productDao.getGlobal(productPojo.getGlobalSkuId());
        if(pojo==null)
            throw new ApiException("product does not exist for given GlobalSkuId: "+productPojo.getGlobalSkuId());
        setProductPojo(productPojo,pojo);
    }
    private void setProductPojo(ProductPojo productPojo, ProductPojo pojo){
        pojo.setDescription(productPojo.getDescription());
        pojo.setName(productPojo.getName());
        pojo.setMrp(productPojo.getMrp());
        pojo.setBrandId(productPojo.getBrandId());
    }
}
