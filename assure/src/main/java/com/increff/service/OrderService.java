package com.increff.service;

import com.increff.dao.OrderDao;
import com.increff.model.enums.OrderStatus;
import com.increff.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public void add(OrderPojo orderPojo) {
        orderDao.add(orderPojo);
    }

    public OrderPojo getOrder(long orderId) throws ApiException {
        OrderPojo orderPojo=orderDao.getOrder(orderId);
        if(orderPojo==null)
            throw new ApiException("Invalid orderId");
        return orderPojo;
    }

    public void updateOrderStatus(long orderId) throws ApiException {
        OrderPojo orderPojo=getOrder(orderId);
        orderPojo.setStatus(OrderStatus.ALLOCATED);
    }
}
