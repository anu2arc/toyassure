package com.increff.assure.controller;

import com.increff.assure.AbstractUnitTest;
import com.increff.assure.model.data.ChannelListingData;
import com.increff.assure.model.forms.ProductForm;
import com.increff.assure.service.ApiException;
import org.h2.command.ddl.CreateUser;
import org.h2.tools.CreateCluster;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.assure.controller.testUtil.*;
import static org.junit.Assert.assertEquals;

public class ChannelListingControllerTest extends AbstractUnitTest {
    @Autowired
    private ChannelListingController channelListingController;
    @Autowired
    private UserController userController;
    @Autowired
    private ProductController productController;
    @Autowired
    private ChannelController channelController;

//    @Test
//    public void testAdd() throws ApiException {
//        // todo this wont work like this will have to create all entries first like client and product and channel
//        channelListingController.add(createChannelListing());
//    }

    @Test
    public void testAddWithEmptyChannelName() throws ApiException {
        try {
            channelListingController.add(createChannelListing("","client1","ch-1","csku-1"));
        } catch (ApiException exception) {
            assertEquals("Channel name cannot be null",exception.getMessage());
        }
    }

    @Test
    public void testAddWithEmptyClientName() throws ApiException {
        try {
            channelListingController.add(createChannelListing("channel","","ch-1","csku-1"));
        } catch (ApiException exception) {
            assertEquals("Client name cannot be null",exception.getMessage());
        }
    }

    @Test
    public void testAdd() throws ApiException {
        userController.add(createClient());

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);

        channelController.add(createChannel());

        channelListingController.add(createChannelListing("channel","client1","ch-1","csku-1"));
    }

    @Test
    public void testAddWithWrongClientName() throws ApiException {
        userController.add(createClient());
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);
        channelController.add(createChannel());
        try {
            channelListingController.add(createChannelListing("channel","someclient","ch-1","csku-1"));
        } catch(ApiException exception) {
            assertEquals("User does not exist for given name: someclient",exception.getMessage());
        }
    }
    @Test
    public void testAddWithWrongChannelName() throws ApiException {
        userController.add(createClient());
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);
        channelController.add(createChannel());
        try {
            channelListingController.add(createChannelListing("somechannel","client1","ch-1","csku-1"));
        } catch(ApiException exception) {
            assertEquals("channel does not exists by given name",exception.getMessage());
        }
    }
    @Test
    public void testAddWithEmptyChannelSkuId() throws ApiException {
        userController.add(createClient());
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);
        channelController.add(createChannel());
        try {
            channelListingController.add(createChannelListing("channel","client1","","csku-1"));
        } catch(ApiException exception) {
            assertEquals("Channel SkuId cannot be null",exception.getMessage());
        }
    }
    @Test
    public void testAddWithEmptyClientSkuId() throws ApiException {
        userController.add(createClient());
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);
        channelController.add(createChannel());
        try {
            channelListingController.add(createChannelListing("channel","client1","ch-1",""));
        } catch(ApiException exception) {
            assertEquals("Client SkuId cannot be null",exception.getMessage());
        }
    }
    @Test
    public void testAddWithWrongProductId() throws ApiException {
        userController.add(createClient());
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);
        channelController.add(createChannel());
        try {
            channelListingController.add(createChannelListing("channel","client1","ch-1","csku-2"));
        } catch(ApiException exception) {
            assertEquals("Product doesn't exist for given client SKU ID",exception.getMessage());
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        userController.add(createClient());
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userController.getAll().get(0).getId(),productFormList);
        channelController.add(createChannel());
        channelListingController.add(createChannelListing("channel","client1","ch-1","csku-1"));

        List<ChannelListingData> channelListingDataList=channelListingController.getAll();

        assertEquals(1,channelListingDataList.size());
    }
}
