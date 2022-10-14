package com.increff.assure.service;

import com.increff.assure.dao.UserDao;
import com.increff.assure.pojo.UserPojo;
import com.increff.commons.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Transactional
    public void add(UserPojo userPojo) throws ApiException {
        checkPair(userPojo);
        userDao.add(userPojo);
    }

    public List<UserPojo> getAll() {
        return userDao.getAll();
    }

    public UserPojo get(long id) throws ApiException {
        UserPojo userPojo= userDao.get(id);
        if(userPojo==null)
            throw new ApiException("User does not exist for given ID: "+id);
        return userPojo;
    }
    public void checkPair(UserPojo userPojo) throws ApiException {
        UserPojo presentPojo= userDao.checkPair(userPojo);
        if(presentPojo!=null)
            throw new ApiException("User already exist");
    }

    public UserPojo getByName(String clientName, ClientType clientType) throws ApiException {
        UserPojo userPojo = userDao.getByName(clientName,clientType);
        if(userPojo ==null)
            throw new ApiException("User does not exist for given name: "+clientName);
        return userPojo;
    }

    public void checkIdAndType(long clientId, ClientType type) throws ApiException {
        if(userDao.checkIdAndType(clientId,type)==null)
            throw new ApiException(type.toString()+" does not exist for given id");
    }
}
