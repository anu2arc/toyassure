package com.increff.model.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderForm {
    private Long clientId;
    private Long channelOrderId;
    private Long customerId;
    private List<OrderItemForm> orderItems;
}
