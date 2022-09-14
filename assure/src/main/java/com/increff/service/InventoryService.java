package com.increff.service;

import com.increff.dao.InventoryDao;
import com.increff.pojo.BinSkuPojo;
import com.increff.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Transactional
    public void add(List<InventoryPojo> inventoryPojoList) {
        for(InventoryPojo inventoryPojo:inventoryPojoList){
            try{
                InventoryPojo presentPojo=check(inventoryPojo.getGlobalSkuId());
                presentPojo.setAvailableQuantity(presentPojo.getAvailableQuantity()+inventoryPojo.getAvailableQuantity());
            } catch (ApiException exception) {
                inventoryDao.add(inventoryPojo);
            }

//            InventoryPojo inventoryPojo1=inventoryDao.get(inventoryPojo.getGlobalSkuId());
//            if(inventoryPojo1==null)
//                inventoryDao.add(inventoryPojo);
//            else {
//                inventoryPojo1.setAvailableQuantity(inventoryPojo1.getAvailableQuantity()+inventoryPojo.getAvailableQuantity());
//            }
        }
    }
    public InventoryPojo check(long globalSkuId) throws ApiException {
        InventoryPojo presentPojo=inventoryDao.get(globalSkuId);
        if(presentPojo==null)
            throw new ApiException("No record for given id:"+globalSkuId);
        return presentPojo;
    }

    @Transactional
    public void update(Long globalSkuId, Long quantity, long quantity1) throws ApiException {
        InventoryPojo inventoryPojo=check(globalSkuId);
        inventoryPojo.setAvailableQuantity(inventoryPojo.getAvailableQuantity()-quantity+quantity1);
    }
}
