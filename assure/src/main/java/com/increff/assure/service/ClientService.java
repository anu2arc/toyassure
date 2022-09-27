package com.increff.assure.service;

import com.increff.assure.dao.ClientDao;
import com.increff.assure.pojo.ClientPojo;
import com.increff.commons.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class ClientService {

    @Autowired
    private ClientDao clientDao;
    @Transactional
    public void add(ClientPojo userPojo) throws ApiException {
        checkPair(userPojo);
        clientDao.add(userPojo);
    }

    public List<ClientPojo> getAll() {
        return clientDao.getAll();
    }

    public ClientPojo get(long id) throws ApiException {
        ClientPojo userPojo= clientDao.get(id);
        if(userPojo==null)
            throw new ApiException("client does not exist for given ID: "+id);
        return userPojo;
    }
    public void checkPair(ClientPojo userPojo) throws ApiException {
        ClientPojo presentPojo= clientDao.checkPair(userPojo);
        if(presentPojo!=null)
            throw new ApiException("User already exist");
    }

    public ClientPojo getByName(String clientName, ClientType clientType) throws ApiException {
        ClientPojo clientPojo= clientDao.getByName(clientName,clientType);
        if(clientPojo==null)
            throw new ApiException("client does not exist for given name: "+clientName);
        return clientPojo;
    }

    public void checkIdAndType(long clientId, ClientType type) throws ApiException {
        if(clientDao.checkIdAndType(clientId,type)==null)
            throw new ApiException("client does not exist for given id and type");
    }
}