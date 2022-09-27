package com.increff.assure.dto;

import com.increff.assure.Util.ClientUtil;
import com.increff.assure.model.data.ClientData;
import com.increff.assure.model.forms.ClientForm;
import com.increff.assure.service.ApiException;
import com.increff.assure.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ClientDto {
    @Autowired
    private ClientService clientService;

    public void add(ClientForm clientForm) throws ApiException {
        ClientUtil.validate(clientForm);
        clientService.add(DtoHelper.convertClientFormToPojo(clientForm));
    }
    public ClientData get(long clientId) throws ApiException {
        return DtoHelper.convertClientPojoToClientData(clientService.get(clientId));
    }
    public List<ClientData> getAll() {
        return DtoHelper.convertClientPojoToClientDataList(clientService.getAll());
    }
}
