package com.increff.commons.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderForm {
    private String channelName;
    private Long clientId;
    private Long customerId;
    private String channelOrderId;
    private List<ItemList> items;
}
