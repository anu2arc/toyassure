package com.increff.assure.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class UserForm {
    @NotNull
    private String name;
    @NotNull
    private String clientType;
}
