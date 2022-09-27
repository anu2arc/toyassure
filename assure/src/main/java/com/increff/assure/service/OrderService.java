package com.increff.assure.service;

import com.increff.assure.dao.OrderDao;
import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new ApiException("order does not exists for given ID");
        return orderPojo;
    }

    public void updateOrderStatus(long orderId, OrderStatus orderStatus) throws ApiException {
        OrderPojo orderPojo=getOrder(orderId);
        orderPojo.setStatus(orderStatus);
    }

    public List<OrderPojo> getAll() {
        return orderDao.getAll();
    }
}
