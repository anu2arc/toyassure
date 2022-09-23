package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ClientForm {
    @NotNull
    private String name;
    @NotNull
    private String clientType;
}
