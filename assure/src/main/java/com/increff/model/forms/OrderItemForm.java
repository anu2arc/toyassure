package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemForm {
    @NotNull
    private String clientSkuId;
    private Long orderedQuantity;
    private Double sellingPricePerUnit;
}
