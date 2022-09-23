package com.increff.service;

import com.increff.dao.OrderItemDao;
import com.increff.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;
    @Transactional
    public void add(List<OrderItemPojo> orderItemPojoList) {
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            orderItemDao.insert(orderItemPojo);
        }
    }

    public List<OrderItemPojo> getItemsByOrderId(long orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=orderItemDao.getItemsByOrderId(orderId);
        if(orderItemPojoList==null)
            throw new ApiException("No item's for given order id");
        return orderItemPojoList;
    }

    @Transactional
    public void allocateQuantity(Long id, long newAllocatedQuantity) throws ApiException {
        OrderItemPojo orderItemPojo=orderItemDao.get(id);
        if(orderItemPojo==null)
            throw new ApiException("order item does not exists for given ID");
        orderItemPojo.setAllocatedQuantity(orderItemPojo.getAllocatedQuantity()+newAllocatedQuantity);
    }

    @Transactional
    public void fulfillOrder(long id) {
        OrderItemPojo orderItemPojo= orderItemDao.get(id);
        if(orderItemPojo==null)
            throw new ArrayIndexOutOfBoundsException("Order Item does not exists for given ID");
        orderItemPojo.setAllocatedQuantity(0L);
        orderItemPojo.setFulfilledQuantity(orderItemPojo.getOrderedQuantity());
    }

    public boolean getOrderStatus(OrderItemPojo orderItemPojo) {
        OrderItemPojo pojo= orderItemDao.get(orderItemPojo.getId());
        return Objects.equals(pojo.getOrderedQuantity(), pojo.getAllocatedQuantity());
    }
}
