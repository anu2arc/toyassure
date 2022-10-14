package com.increff.assure.Util;

import com.increff.assure.model.forms.UserForm;
import com.increff.assure.service.ApiException;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Objects;

@Repository
public class ClientUtil {
    public static void validate(UserForm userForm) throws ApiException {
        if(Objects.isNull(userForm.getName()) || userForm.getName().trim().equals(""))
            throw new ApiException("Username cannot be empty");
        if(userForm.getName().length()>30)
            throw new ApiException("Username too long");
        if(Objects.isNull(userForm.getClientType()))
            throw new ApiException("Usertype cannot be empty");
        normalize(userForm);
        if(!userForm.getClientType().equals("CLIENT") && !userForm.getClientType().equals("CUSTOMER"))
            throw new ApiException("Invalid user type");
    }
    private static void normalize(UserForm userForm){
        userForm.setName(userForm.getName().trim().toLowerCase(Locale.ROOT));
        userForm.setClientType(userForm.getClientType().trim().toUpperCase());
    }
}
