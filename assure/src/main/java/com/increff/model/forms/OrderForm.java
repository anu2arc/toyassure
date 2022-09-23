package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderForm {
    @NotNull
    private String clientName;
    @NotNull
    private String channelOrderId;
    @NotNull
    private String customerName;
    private List<OrderItemForm> orderItems;
}
