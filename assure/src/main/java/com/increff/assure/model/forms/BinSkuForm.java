package com.increff.assure.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BinSkuForm {
    private Long binId;
    @NotNull
    private String clientSkuId;
    private Long quantity;
}
