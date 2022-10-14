package com.increff.assure.controller;

import com.increff.assure.AbstractUnitTest;
import com.increff.assure.model.data.ProductData;
import com.increff.assure.model.data.UserData;
import com.increff.assure.model.forms.ProductForm;
import com.increff.assure.model.forms.ProductUpdateForm;
import com.increff.assure.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.assure.controller.testUtil.*;
import static org.junit.Assert.assertEquals;

public class ProductControllerTest extends AbstractUnitTest {
    @Autowired
    private ProductController productController;
    @Autowired
    private UserController userController;

    @Test
    public void testAdd() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);
    }

    @Test
    public void testAddWithAlreadyExistingProduct() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);
        try{
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("Product already exist",exception.getMessage());
        }
    }

    @Test
    public void testAddWithWrongClientId() throws ApiException {
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        try {
            productController.add(100, productFormList);
        } catch (ApiException exception){
            assertEquals("CLIENT does not exist for given id",exception.getMessage());
        }
    }

    @Test
    public void testAddWithDuplicateProduct() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct());
        productFormList.add(createProduct());
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("Duplicate entry for product :csku-1",exception.getMessage());
        }
    }

    @Test
    public void testAddWithInvalidClientSkuId() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("","p1","bid-1",100.0,"desc"));
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("ClientSkuId cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testAddWithInvalidName() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("csku-1","","bid-1",100.0,"desc"));
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("Name cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testAddWithInvalidBrandId() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("csku-1","p1","",100.0,"desc"));
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("BrandId cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testAddWithZeroMrp() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("csku-1","p1","bid-1",0.0,"desc"));
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("MRP cannot be zero",exception.getMessage());
        }
    }

    @Test
    public void testAddWithNegativeMrp() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("csku-1","p1","bid-1",-10.0,"desc"));
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("MRP cannot be negative",exception.getMessage());
        }
    }

    @Test
    public void testAddWithEmptyDescription() throws ApiException {
        userController.add(createClient());
        UserData userData=userController.getAll().get(0);
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("csku-1","p1","bid-1",10.0,""));
        try {
            productController.add(userData.getId(), productFormList);
        } catch (ApiException exception){
            assertEquals("Description cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testGetAll() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        List<ProductData> productDataList=productController.getAll();
        assertEquals(1,productDataList.size());
    }

    @Test
    public void testUpdate() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        ProductData productData=productController.getAll().get(0);
        ProductUpdateForm productUpdateForm=productUpdateForm("newname","nbid-1",200.0,"ndesc");

        productController.update(productData.getGlobalSkuId(),productUpdateForm);
        ProductData updatedData=productController.getAll().get(0);

        assertEquals(updatedData.getName(),productUpdateForm.getName());
        assertEquals(updatedData.getMrp(),productUpdateForm.getMrp());
        assertEquals(updatedData.getDescription(),productUpdateForm.getDescription());
        assertEquals(updatedData.getBrandId(),productUpdateForm.getBrandId());
    }

    @Test
    public void testUpdateWithEmptyName() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        ProductData productData=productController.getAll().get(0);
        ProductUpdateForm productUpdateForm=productUpdateForm("","nbid-1",200.0,"ndesc");

        try {
            productController.update(productData.getGlobalSkuId(), productUpdateForm);
        } catch (ApiException exception){
            assertEquals("Name cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testUpdateWithEmptyBrandId() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        ProductData productData=productController.getAll().get(0);
        ProductUpdateForm productUpdateForm=productUpdateForm("name","",200.0,"ndesc");

        try {
            productController.update(productData.getGlobalSkuId(), productUpdateForm);
        } catch (ApiException exception){
            assertEquals("BrandId cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testUpdateWithEmptyDescription() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        ProductData productData=productController.getAll().get(0);
        ProductUpdateForm productUpdateForm=productUpdateForm("name","bid",200.0,"");

        try {
            productController.update(productData.getGlobalSkuId(), productUpdateForm);
        } catch (ApiException exception){
            assertEquals("Description cannot be empty",exception.getMessage());
        }
    }

    @Test
    public void testUpdateWithNegativeMrp() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        ProductData productData=productController.getAll().get(0);
        ProductUpdateForm productUpdateForm=productUpdateForm("name","bid",-200.0,"ndesc");

        try {
            productController.update(productData.getGlobalSkuId(), productUpdateForm);
        } catch (ApiException exception){
            assertEquals("MRP cannot be negative",exception.getMessage());
        }
    }
    @Test
    public void testUpdateWithZeroMrp() throws ApiException {
        userController.add(createClient());
        UserData userData = userController.getAll().get(0);

        List<ProductForm> productFormList = new ArrayList<>();
        productFormList.add(createProduct());
        productController.add(userData.getId(), productFormList);

        ProductData productData=productController.getAll().get(0);
        ProductUpdateForm productUpdateForm=productUpdateForm("name","bid-1",0.0,"ndesc");

        try {
            productController.update(productData.getGlobalSkuId(), productUpdateForm);
        } catch (ApiException exception){
            assertEquals("MRP cannot be zero",exception.getMessage());
        }
    }
}
