package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    String clientSkuId;
    String name;
    String brandId;
    Double mrp;
    String description;
}
