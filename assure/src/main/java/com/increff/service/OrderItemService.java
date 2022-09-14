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
}
