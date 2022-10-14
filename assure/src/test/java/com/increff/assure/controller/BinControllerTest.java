package com.increff.assure.controller;

import com.increff.assure.AbstractUnitTest;
import com.increff.assure.model.data.BinData;
import com.increff.assure.model.data.BinSkuData;
import com.increff.assure.model.data.UserData;
import com.increff.assure.model.forms.BinSkuForm;
import com.increff.assure.model.forms.ProductForm;
import com.increff.assure.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.assure.controller.testUtil.*;
import static org.junit.Assert.assertEquals;

public class BinControllerTest extends AbstractUnitTest {
    @Autowired
    private BinController binController;

    @Autowired
    private UserController userController;

    @Autowired
    private ProductController productController;

    @Test
    public void testCreateBin(){
        binController.create(2);
    }

    @Test
    public void testGetAll(){
        binController.create(2);
        List<BinData> binDataList=binController.getAll();
        assertEquals(2,binDataList.size());
    }

    @Test
    public void testAdd() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", 10L));
        binController.add(userData.getId(), binSkuFormList);
    }

    @Test
    public void testAddWithWrongClientSkuId() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-2", 10L));
        try {
            binController.add(userData.getId(), binSkuFormList);
        } catch (ApiException exception) {
            assertEquals("Product doesn't exist for given ClientSkuId",exception.getMessage());
        }
    }

    @Test
    public void testAddWithWrongBinId() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(123L,"csku-1", 10L));
        try {
            binController.add(userData.getId(), binSkuFormList);
        } catch (ApiException exception) {
            assertEquals("Invalid BinId:123",exception.getMessage());
        }
    }

    @Test
    public void testAddWithEmptyClientSkuId() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"", 10L));
        try {
            binController.add(userData.getId(), binSkuFormList);
        } catch (ApiException exception) {
            assertEquals("ClientSkuId cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testAddWithNegativeQuantity() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", -10L));
        try {
            binController.add(userData.getId(), binSkuFormList);
        } catch (ApiException exception) {
            assertEquals("Quantity cannot be negative",exception.getMessage());
        }
    }

    @Test
    public void testAddToExistingInventory() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", 10L));
        binController.add(userData.getId(), binSkuFormList);
        binController.add(userData.getId(), binSkuFormList);
    }

    @Test
    public void testGetBinSku() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", 10L));
        binController.add(userData.getId(), binSkuFormList);

        List<BinSkuData> binSkuDataList=binController.getBinSku();

        assertEquals(1,binSkuDataList.size());
    }

    @Test
    public void testUpdate() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", 10L));
        binController.add(userData.getId(), binSkuFormList);

        BinSkuData binSkuData=binController.getBinSku().get(0);

        binController.update(binSkuData.getId(),createUpdateForm(100L));
        BinSkuData updatedBinSkuData=binController.getBinSku().get(0);

        assertEquals(updatedBinSkuData.getQuantity(),100L);
        assertEquals(updatedBinSkuData.getGlobalSkuId(),binSkuData.getGlobalSkuId());
    }

    @Test
    public void testUpdateWithWrongId() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", 10L));
        binController.add(userData.getId(), binSkuFormList);

        BinSkuData binSkuData=binController.getBinSku().get(0);

        try {
            binController.update(binSkuData.getId(),createUpdateForm(-100L));
        } catch (ApiException exception){
            assertEquals("Quantity cannot be negative",exception.getMessage());
        }
    }

    @Test
    public void testUpdateWithNegativeQuantity() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);

        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        binController.create(2);
        List<BinData> binDataList=binController.getAll();

        List<BinSkuForm> binSkuFormList=new ArrayList<>();
        binSkuFormList.add(createBin(binDataList.get(0).getBinId(),"csku-1", 10L));
        binController.add(userData.getId(), binSkuFormList);

        BinSkuData binSkuData=binController.getBinSku().get(0);

        try {
            binController.update(123L,createUpdateForm(100L));
        } catch (ApiException exception){
            assertEquals("Bin does not exist for given ID",exception.getMessage());
        }
    }
}
