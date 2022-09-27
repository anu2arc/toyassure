package com.increff.commons.data;

import com.increff.commons.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData {
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;
    private OrderStatus status;
}
