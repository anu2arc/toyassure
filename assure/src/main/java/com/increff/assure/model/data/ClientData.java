package com.increff.assure.model.data;

import com.increff.commons.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientData {
    private String name;
    private ClientType type;
}
