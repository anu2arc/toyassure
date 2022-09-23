package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductForm {
    @NotNull
    String clientSkuId;
    @NotNull
    String name;
    @NotNull
    String brandId;
    Double mrp;
    @NotNull
    String description;
}
