package com.increff.dto;

import com.increff.model.data.ClientData;
import com.increff.model.form.ClientForm;
import com.increff.pojo.ClientPojo;
import com.increff.service.ApiException;
import com.increff.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.Util.ClientUtil.validate;
import static com.increff.dto.DtoHelper.convert;
@Repository
public class ClientDto {
    @Autowired
    private ClientService clientService;

    public void add(ClientForm clientForm) throws ApiException {
        validate(clientForm);
        clientService.add(convert(clientForm));
    }
    public ClientData get(long clientId) throws ApiException {
        return convert(clientService.get(clientId));
    }
    public List<ClientData> getAll() {
        List<ClientPojo> userPojoList= clientService.getAll();;
        List<ClientData> clientData =new ArrayList<>();
        for(ClientPojo userPojo:userPojoList) {
            clientData.add(convert(userPojo));
        }
        return clientData;
    }
}
