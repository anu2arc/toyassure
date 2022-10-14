package com.increff.assure.model.data;

import com.increff.commons.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private Long id;
    private String name;
    private ClientType type;
}
