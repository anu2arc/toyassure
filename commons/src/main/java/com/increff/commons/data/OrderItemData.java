package com.increff.commons.data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData {
    private Long globalSkuId;
    private Long quantity;
    private Double sellingPrice;
}
