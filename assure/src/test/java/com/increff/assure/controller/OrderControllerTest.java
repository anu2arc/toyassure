package com.increff.assure.controller;

import com.increff.assure.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class OrderControllerTest extends AbstractUnitTest {
    @Autowired
    private ChannelListingController channelListingController;
    @Autowired
    private UserController userController;
    @Autowired
    private ProductController productController;
    @Autowired
    private ChannelController channelController;
    @Autowired
    private OrderController orderController;

    @Test
    public void test(){
        assertEquals(1,1);
    }
}
