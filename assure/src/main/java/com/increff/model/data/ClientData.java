package com.increff.model.data;

import com.increff.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientData {
    private String name;
    private ClientType type;
}
