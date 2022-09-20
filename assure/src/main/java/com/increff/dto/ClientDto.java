package com.increff.dto;

import com.increff.model.data.ClientData;
import com.increff.model.forms.ClientForm;
import com.increff.pojo.ClientPojo;
import com.increff.service.ApiException;
import com.increff.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.ClientUtil.validate;
import static com.increff.dto.DtoHelper.*;

@Service

public class ClientDto {
    @Autowired
    private ClientService clientService;

    public void add(ClientForm clientForm) throws ApiException {
        validate(clientForm);
        clientService.add(convertClientFormToPojo(clientForm));
    }
    public ClientData get(long clientId) throws ApiException {
        return convertClientPojoToClientData(clientService.get(clientId));
    }
    public List<ClientData> getAll() {
        return convertClientPojoToClientDataList(clientService.getAll());
    }
}
