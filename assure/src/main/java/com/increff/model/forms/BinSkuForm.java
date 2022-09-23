package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BinSkuForm {
    private long binId;
    @NotNull
    private String clientSkuId;
    private long quantity;
}
