package com.increff.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateForm {
    String name;
    String brandId;
    Double mrp;
    String description;
}
