package com.increff.assure.dto;

import com.increff.assure.Util.ClientUtil;
import com.increff.assure.model.data.UserData;
import com.increff.assure.model.forms.UserForm;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserDto {
    @Autowired
    private UserService userService;

    public void add(UserForm userForm) throws ApiException {
        ClientUtil.validate(userForm);
        userService.add(DtoHelper.convertClientFormToPojo(userForm));
    }
    public UserData get(long clientId) throws ApiException {
        return DtoHelper.convertClientPojoToClientData(userService.get(clientId));
    }
    public List<UserData> getAll() {
        return DtoHelper.convertClientPojoToClientDataList(userService.getAll());
    }
}
