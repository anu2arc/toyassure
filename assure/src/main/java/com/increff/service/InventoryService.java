package com.increff.service;

import com.increff.dao.InventoryDao;
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
        }
    }
    public InventoryPojo check(long globalSkuId) throws ApiException {
        InventoryPojo presentPojo=inventoryDao.get(globalSkuId);
        if(presentPojo==null)
            throw new ApiException("No record for given id:"+globalSkuId);
        return presentPojo;
    }

    @Transactional
    public void allocateInventory(Long globalSkuId, Long allocatedQuantity) throws ApiException {
        InventoryPojo inventoryPojo=check(globalSkuId);
        inventoryPojo.setAvailableQuantity(inventoryPojo.getAvailableQuantity()-allocatedQuantity);
        inventoryPojo.setAllocatedQuantity(inventoryPojo.getAllocatedQuantity()+allocatedQuantity);
    }
    @Transactional
    public void update(Long globalSkuId, Long oldQuantity, Long newQuantity) throws ApiException {
        InventoryPojo inventoryPojo=check(globalSkuId);
        inventoryPojo.setAvailableQuantity(inventoryPojo.getAvailableQuantity()-oldQuantity+newQuantity);
    }

    @Transactional
    public void fulfillOrder(Long globalSkuId, Long orderedQuantity) throws ApiException {
        InventoryPojo inventoryPojo=check(globalSkuId);
        inventoryPojo.setAllocatedQuantity(inventoryPojo.getAllocatedQuantity()-orderedQuantity);
        inventoryPojo.setFulfilledQuantity(inventoryPojo.getFulfilledQuantity()+orderedQuantity);
    }
}
