package com.increff.service;

import com.increff.dao.OrderItemDao;
import com.increff.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<OrderItemPojo> getItemsByOrderId(long orderId) {
        return orderItemDao.getItemsByOrderId(orderId);
    }

    @Transactional
    public void allocateQuantity(Long id, long newAllocatedQuantity) throws ApiException {
        OrderItemPojo orderItemPojo=orderItemDao.get(id);
        if(orderItemPojo==null)
            throw new ApiException("Invalid id for orderItem");
        orderItemPojo.setAllocatedQuantity(orderItemPojo.getAllocatedQuantity()+newAllocatedQuantity);
    }

    @Transactional
    public void fulfillOrder(long id) {
        OrderItemPojo orderItemPojo= orderItemDao.get(id);
        if(orderItemPojo==null)
            throw new ArrayIndexOutOfBoundsException("Invalid id for orderItem");
        orderItemPojo.setAllocatedQuantity(0L);
        orderItemPojo.setFulfilledQuantity(orderItemPojo.getOrderedQuantity());
    }
}
