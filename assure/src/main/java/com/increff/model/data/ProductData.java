package com.increff.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData {
    Long globalSkuId;
    String clientSkuId;
    Long clientId;
    String name;
    String brandId;
    Double mrp;
    String description;
}
