package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderForm {
    private String clientName;
    private String channelOrderId;
    private String customerName;
    private List<OrderItemForm> orderItems;
}