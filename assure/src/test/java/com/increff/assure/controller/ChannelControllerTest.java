package com.increff.assure.controller;

import com.increff.assure.AbstractUnitTest;
import com.increff.assure.model.data.ChannelData;
import com.increff.assure.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.assure.controller.testUtil.createChannel;
import static org.junit.Assert.assertEquals;

public class ChannelControllerTest extends AbstractUnitTest {
    @Autowired
    private ChannelController channelController;

    @Test
    public void testAdd() throws ApiException {
        channelController.add(createChannel());
    }

    @Test
    public void testAddWithDuplicateEntry() throws ApiException {
        channelController.add(createChannel());
        try {
            channelController.add(createChannel());
        } catch (ApiException exception){
            assertEquals("Channel already exist",exception.getMessage());
        }
    }

    @Test
    public void testAddWithEmptyName() throws ApiException {
        try {
            channelController.add(createChannel("", "self"));
        } catch (ApiException exception){
            assertEquals("Channel name cannot be null",exception.getMessage());
        }
    }

    @Test
    public void testAddWithEmptyInvoiceType() throws ApiException {
        try {
            channelController.add(createChannel("channel", ""));
        } catch (ApiException exception){
            assertEquals("Invoice type cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testAddWithInvalidInvoiceType() throws ApiException {
        try {
            channelController.add(createChannel("channel", "external"));
        } catch (ApiException exception){
            assertEquals("Invalid invoice type",exception.getMessage());
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        channelController.add(createChannel());
        List<ChannelData> channelDataList=channelController.getAll();
        assertEquals(2,channelDataList.size());
    }
}
