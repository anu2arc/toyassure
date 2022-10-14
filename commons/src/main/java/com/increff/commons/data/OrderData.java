package com.increff.commons.data;

import com.increff.commons.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData {
    private Long id;
    private String clientName;
    private String customerName;
    private String channelName;
    private String channelOrderId;
    private OrderStatus status;
}
