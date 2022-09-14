package com.increff.service;

import com.increff.dao.OrderDao;
import com.increff.dto.OrderDto;
import com.increff.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public void add(OrderPojo orderPojo) {
        orderDao.add(orderPojo);
    }
}
