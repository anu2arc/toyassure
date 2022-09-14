package com.increff.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemForm {
    private String clientSkuId;
    private Long orderedQuantity;
    private Double sellingPricePerUnit;
}
