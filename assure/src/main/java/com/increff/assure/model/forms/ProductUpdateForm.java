package com.increff.assure.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductUpdateForm {
    @NotNull
    private String name;
    @NotNull
    private String brandId;
    private Double mrp;
    @NotNull
    private String description;
}
