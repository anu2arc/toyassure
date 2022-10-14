package com.increff.assure.controller;

import com.increff.assure.model.forms.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class testUtil {
    public static UserForm createUser(String name, String type){
        UserForm userForm =new UserForm();
        userForm.setClientType(type);
        userForm.setName(name);
        return userForm;
    }
    public static UserForm createClient(){
        return createUser("client1","client");
    }
    public static UserForm createCustomer(){
        return createUser("customer1","customer");
    }
    public static ProductForm createProduct(String clientSkuId, String name,String brandId,Double mrp,String description) {
        ProductForm productForm = new ProductForm();
        productForm.setClientSkuId(clientSkuId);
        productForm.setName(name);
        productForm.setBrandId(brandId);
        productForm.setMrp(mrp);
        productForm.setDescription(description);
        return productForm;
    }
    public static ProductForm createProduct(){
        return createProduct("csku-1","p1","bid-1",100.0,"desc");
    }
    public static List<ProductForm> createProductFormList(){
        List<ProductForm> productFormList=new ArrayList<>();
        productFormList.add(createProduct("csku-1","p1","bid-1",100.0,"desc"));
        productFormList.add(createProduct("csku-2","p2","bid-2",200.0,"desc"));
        return productFormList;
    }
    public static ProductUpdateForm productUpdateForm(String name,String brandId,Double mrp,String description){
        ProductUpdateForm productUpdateForm=new ProductUpdateForm();
        productUpdateForm.setDescription(description);
        productUpdateForm.setMrp(mrp);
        productUpdateForm.setName(name);
        productUpdateForm.setBrandId(brandId);
        return productUpdateForm;
    }
    public static BinSkuForm createBin(Long binId,String clientSkuId,Long quantity){
        BinSkuForm binSkuForm=new BinSkuForm();
        binSkuForm.setBinId(binId);
        binSkuForm.setClientSkuId(clientSkuId);
        binSkuForm.setQuantity(quantity);
        return binSkuForm;
    }

    public static BinSkuUpdateForm createUpdateForm(Long quantity){
        BinSkuUpdateForm binSkuUpdateForm=new BinSkuUpdateForm();
        binSkuUpdateForm.setQuantity(quantity);
        return binSkuUpdateForm;
    }

    public static ChannelForm createChannel(String name,String type){
        ChannelForm channelForm=new ChannelForm();
        channelForm.setName(name);
        channelForm.setInvoiceType(type);
        return channelForm;
    }
    public static ChannelForm createChannel(){
        return createChannel("channel","self");
    }

    public static ChannelListingForm createChannelForm(String channelSkuId,String clientSkuId){
        ChannelListingForm channelListingForm=new ChannelListingForm();
        channelListingForm.setChannelSkuId(channelSkuId);
        channelListingForm.setClientSkuId(clientSkuId);
        return channelListingForm;
    }
    public static List<ChannelListingForm> createChannelFormList(String channelSkuId,String clientSkuId){
        List<ChannelListingForm> channelListingFormList=new ArrayList<>();
        channelListingFormList.add(createChannelForm(channelSkuId,clientSkuId));
        return channelListingFormList;
    }
    public static ChannelListingUploadForm createChannelListing(String channelName,String clientName,String channelSkuId,String clientSkuId){
        ChannelListingUploadForm channelListingUploadForm=new ChannelListingUploadForm();
        channelListingUploadForm.setChannelName(channelName);
        channelListingUploadForm.setClientName(clientName);
        channelListingUploadForm.setChannelList(createChannelFormList(channelSkuId,clientSkuId));
        return  channelListingUploadForm;
    }
    public static ChannelListingUploadForm createChannelListing(){
        return createChannelListing("channel","client1","ch-1","csku-1");
    }
}
