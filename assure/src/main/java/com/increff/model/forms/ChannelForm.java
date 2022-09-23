package com.increff.model.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChannelForm {
    @NotNull
    private String name;
    @NotNull
    private String invoiceType;
}
