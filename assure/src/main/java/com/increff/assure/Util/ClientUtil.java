package com.increff.assure.Util;

import com.increff.assure.model.forms.ClientForm;
import com.increff.assure.service.ApiException;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Objects;

@Repository
public class ClientUtil {
    public static void validate(ClientForm clientForm) throws ApiException {
        if(Objects.isNull(clientForm.getName()) || clientForm.getName().trim().equals(""))
            throw new ApiException("Username cannot be empty");
        if(clientForm.getName().length()>30)
            throw new ApiException("Username too long");
        if(Objects.isNull(clientForm.getClientType()))
            throw new ApiException("Usertype cannot be empty");
        normalize(clientForm);
        if(!clientForm.getClientType().equals("CLIENT") && !clientForm.getClientType().equals("CUSTOMER"))
            throw new ApiException("Invalid user type");
    }
    private static void normalize(ClientForm clientForm){
        clientForm.setName(clientForm.getName().trim().toLowerCase(Locale.ROOT));
        clientForm.setClientType(clientForm.getClientType().trim().toUpperCase());
    }
}
