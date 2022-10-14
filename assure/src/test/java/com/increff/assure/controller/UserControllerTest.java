package com.increff.assure.controller;

import com.increff.assure.AbstractUnitTest;
import com.increff.assure.model.data.UserData;
import com.increff.assure.model.forms.UserForm;
import com.increff.assure.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.ls.LSException;

import java.util.List;

import static com.increff.assure.controller.testUtil.*;
import static org.junit.Assert.assertEquals;

public class UserControllerTest extends AbstractUnitTest {
    @Autowired
    private UserController userController;

    @Test
    public void testAddClient() throws ApiException {
        UserForm userForm =createClient();
        userController.add(userForm);
    }

    @Test
    public void testAddCustomer() throws ApiException {
        UserForm userForm =createCustomer();
        userController.add(userForm);
    }

    @Test
    public void testDuplicateCustomerAdd() throws ApiException {
        userController.add(createCustomer());
        try{
            userController.add(createCustomer());
        }
        catch (ApiException exception){
            assertEquals("User already exist",exception.getMessage());
        }
    }

    @Test
    public void testDuplicateClientAdd() throws ApiException {
        userController.add(createClient());
        try{
            userController.add(createClient());
        }
        catch (ApiException exception){
            assertEquals("User already exist",exception.getMessage());
        }
    }

    @Test
    public void testInvalidUsertype(){
        try {
            userController.add(createUser("user1","user"));
        } catch (ApiException exception) {
            assertEquals("Invalid user type",exception.getMessage());
        }
    }

    @Test
    public void testEmptyUserType(){
        try {
            userController.add(createUser("user1",""));
        } catch (ApiException exception) {
            assertEquals("Invalid user type",exception.getMessage());
        }
    }

    @Test
    public void testUserNameTooLong(){
        try {
            userController.add(createUser("user1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","Client"));
        } catch (ApiException exception) {
            assertEquals("Username too long",exception.getMessage());
        }
    }

    @Test
    public void testEmptyUserName(){
        try {
            userController.add(createUser("",""));
        } catch (ApiException exception) {
            assertEquals("Username cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        userController.add(createClient());
        userController.add(createCustomer());
        List<UserData> userDataList=userController.getAll();
        assertEquals(2,userDataList.size());
    }

    @Test
    public void testGet() throws ApiException {
        userController.add(createClient());
        UserData userData= userController.getAll().get(0);
        UserData fetchedUserData=userController.get(userData.getId());

        assertEquals(userData.getType(),fetchedUserData.getType());
        assertEquals(userData.getName(),fetchedUserData.getName());
    }

    @Test
    public void testGetWithWrongId() throws ApiException {
        try {
            UserData UserData = userController.get(123);
        } catch (ApiException exception){
            assertEquals("User does not exist for given ID: 123",exception.getMessage());
        }
    }
}
