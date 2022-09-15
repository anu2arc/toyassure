package com.increff.model.forms;

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
