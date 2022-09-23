package com.increff.dto;

import com.increff.form.ChannelOrderForm;
import com.increff.form.ChannelOrderUploadForm;
import com.increff.model.data.*;
import com.increff.model.enums.ClientType;
import com.increff.model.enums.InvoiceType;
import com.increff.model.enums.OrderStatus;
import com.increff.model.forms.*;
import com.increff.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.model.enums.OrderStatus.*;

@Repository
public class DtoHelper {
    public static ClientData convertClientPojoToClientData(ClientPojo userPojo){
        ClientData clientData =new ClientData();
        clientData.setName(userPojo.getName());
        clientData.setType(userPojo.getClientType());
        return clientData;
    }
    public static List<ClientData> convertClientPojoToClientDataList(List<ClientPojo> userPojoList) {
        List<ClientData> clientData =new ArrayList<>();
        for(ClientPojo userPojo:userPojoList) {
            clientData.add(convertClientPojoToClientData(userPojo));
        }
        return clientData;
    }
    public static ClientPojo convertClientFormToPojo(ClientForm clientForm){
        ClientPojo userPojo=new ClientPojo();
        userPojo.setClientType(ClientType.valueOf(clientForm.getClientType()));
        userPojo.setName(clientForm.getName());
        return userPojo;
    }
    public static ProductData convertProductPojoToProductData(ProductPojo productPojo){
        ProductData productData=new ProductData();
        productData.setGlobalSkuId(productPojo.getGlobalSkuId());
        productData.setClientSkuId(productPojo.getClientSkuId());
        productData.setName(productPojo.getName());
        productData.setBrandId(productPojo.getBrandId());
        productData.setMrp(productPojo.getMrp());
        productData.setDescription(productPojo.getDescription());
        productData.setClientId(productPojo.getClientId());
        return productData;
    }
    public static List<ProductData> convertProductPojoToProductDataList(List<ProductPojo> productPojoList) {
        List<ProductData> productDataList=new ArrayList<>();
        for(ProductPojo productPojo:productPojoList){
            productDataList.add(convertProductPojoToProductData(productPojo));
        }
        return productDataList;
    }
    public static ProductPojo convertProductFormToProductPojo(ProductForm productForm, long userID){
        ProductPojo productPojo=new ProductPojo();
        productPojo.setClientSkuId(productForm.getClientSkuId());
        productPojo.setClientId(userID);
        productPojo.setName(productForm.getName());
        productPojo.setBrandId(productForm.getBrandId());
        productPojo.setMrp(productForm.getMrp());
        productPojo.setDescription(productForm.getDescription());
        return productPojo;
    }

    public static ProductPojo convertProductFormToPojo(ProductUpdateForm productUpdateForm,long gSkuId){
        ProductPojo productPojo=new ProductPojo();
        productPojo.setGlobalSkuId(gSkuId);
        productPojo.setName(productUpdateForm.getName());
        productPojo.setBrandId(productUpdateForm.getBrandId());
        productPojo.setMrp(productUpdateForm.getMrp());
        productPojo.setDescription(productUpdateForm.getDescription());
        return productPojo;
    }
    public static List<BinData> convertBinPojoToBinDataList(List<BinPojo> binPojoList) {
        List<BinData> binDataList=new ArrayList<>();
        for(BinPojo binPojo:binPojoList){
            binDataList.add(convertBinPojoToBinData(binPojo));
        }
        return binDataList;
    }

    public static BinData convertBinPojoToBinData(BinPojo binPojo) {
        BinData binData=new BinData();
        binData.setBinId(binPojo.getBinId());
        return binData;
    }
    public static List<InventoryPojo> convertBinPojoListToInventoryPojo(List<BinSkuPojo> binSkuPojoList){
        List<InventoryPojo> inventoryPojoList=new ArrayList<>();
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            inventoryPojoList.add(create(binSkuPojo));
        }
        return inventoryPojoList;
    }
    public static BinSkuPojo convertBinSkuFormToBinSkuPojo(BinSkuForm binSkuForm, long globalSkuId){
        BinSkuPojo binSkuPojo=new BinSkuPojo();
        binSkuPojo.setBinId(binSkuForm.getBinId());
        binSkuPojo.setGlobalSkuId(globalSkuId);
        binSkuPojo.setQuantity(binSkuForm.getQuantity());
        return binSkuPojo;
    }
    public static List<BinSkuData> convertBinSkuPojoToBinSkuDataList(List<BinSkuPojo> binSkuPojoList) {
        List<BinSkuData> binSkuDataList=new ArrayList<>();
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            binSkuDataList.add(convertBinSkuPojoToBinSkuData(binSkuPojo));
        }
        return binSkuDataList;
    }

    private static BinSkuData convertBinSkuPojoToBinSkuData(BinSkuPojo binSkuPojo) {
        BinSkuData binSkuData=new BinSkuData();
        binSkuData.setId(binSkuPojo.getId());
        binSkuData.setBinId(binSkuPojo.getBinId());
        binSkuData.setGlobalSkuId(binSkuPojo.getGlobalSkuId());
        binSkuData.setQuantity(binSkuPojo.getQuantity());
        return binSkuData;
    }

    public static ChannelPojo convertChannelFormToPojo(ChannelForm channelForm){
        ChannelPojo channelPojo=new ChannelPojo();
        channelPojo.setName(channelForm.getName());
        channelPojo.setInvoiceType(InvoiceType.valueOf(channelForm.getInvoiceType()));
        return channelPojo;
    }
    private static InventoryPojo create(BinSkuPojo binSkuPojo){
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setGlobalSkuId(binSkuPojo.getGlobalSkuId());
        inventoryPojo.setAvailableQuantity(binSkuPojo.getQuantity());
        inventoryPojo.setAllocatedQuantity(0L);
        inventoryPojo.setFulfilledQuantity(0L);
        return inventoryPojo;
    }
    public static ChannelListingPojo convertListingFormToPojo(Long channelId, String channelSkuId, Long clientId, Long globalSkuId) {
        ChannelListingPojo channelListingPojo=new ChannelListingPojo();
        channelListingPojo.setChannelId(channelId);
        channelListingPojo.setChannelSkuId(channelSkuId);
        channelListingPojo.setClientId(clientId);
        channelListingPojo.setGlobalSkuId(globalSkuId);
        return channelListingPojo;
    }
    public static List<ChannelListingData> convertListingPojoToListingData(List<ChannelListingPojo> channelListingPojoList){
        List<ChannelListingData> channelListingData=new ArrayList<>();
        for(ChannelListingPojo channelListingPojo:channelListingPojoList){
            channelListingData.add(create(channelListingPojo));
        }
        return channelListingData;
    }
    private static ChannelListingData create(ChannelListingPojo channelListingPojo){
        ChannelListingData channelListingData=new ChannelListingData();
        channelListingData.setChannelId(channelListingPojo.getChannelId());
        channelListingData.setChannelSkuId(channelListingPojo.getChannelSkuId());
        channelListingData.setClientId(channelListingPojo.getClientId());
        channelListingData.setGlobalSkuId(channelListingPojo.getGlobalSkuId());
        return channelListingData;
    }
    public static OrderPojo convertToOrderPOJO(OrderForm orderForm, long clientId, long customerId, long channelId){
        OrderPojo orderPojo=new OrderPojo();
        orderPojo.setClientId(clientId);
        orderPojo.setCustomerId(customerId);
        orderPojo.setChannelOrderId(orderPojo.getChannelOrderId());
        orderPojo.setStatus(CREATED);
        orderPojo.setChannelId(channelId);
        return orderPojo;
    }

    public static OrderItemPojo convertToOrderItemPOJO(Long orderId, Long globalSkuId, OrderItemForm orderItemForm) {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setGlobalSkuId(globalSkuId);
        orderItemPojo.setOrderedQuantity(orderItemForm.getOrderedQuantity());
        orderItemPojo.setAllocatedQuantity(0L);
        orderItemPojo.setFulfilledQuantity(0L);
        orderItemPojo.setSellingPricePerUnit(orderItemForm.getSellingPricePerUnit());
        return orderItemPojo;
    }

    public static OrderPojo convertOrderFormToOrderPojo(ChannelOrderUploadForm orderUploadForm, long channelId){
        OrderPojo orderPojo=new OrderPojo();
        orderPojo.setClientId(orderUploadForm.getClientId());
        orderPojo.setCustomerId(orderUploadForm.getCustomerId());
        orderPojo.setChannelId(channelId);
        orderPojo.setChannelOrderId(orderUploadForm.getChannelOrderId());
        orderPojo.setStatus(OrderStatus.CREATED);
        return orderPojo;
    }
    public static OrderItemPojo convertOrderFormToOrderItemPojo(long orderId, ChannelOrderForm orderForm, long globalSkuID) {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setGlobalSkuId(globalSkuID);
        orderItemPojo.setOrderedQuantity(orderForm.getQuantity());
        orderItemPojo.setAllocatedQuantity(0L);
        orderItemPojo.setFulfilledQuantity(0L);
        orderItemPojo.setSellingPricePerUnit(orderForm.getSellingPrice());
        return orderItemPojo;
    }

    public static List<ChannelData> convertChannelPojoToDataList(List<ChannelPojo> channelPojoList) {
        List<ChannelData> channelData=new ArrayList<>();
        for(ChannelPojo channelPojo:channelPojoList){
            channelData.add(convertChannelPojoToData(channelPojo));
        }
        return channelData;
    }

    public static ChannelData convertChannelPojoToData(ChannelPojo channelPojo) {
        ChannelData channelData=new ChannelData();
        channelData.setInvoiceType(channelPojo.getInvoiceType().toString());
        channelData.setName(channelPojo.getName());
        return channelData;
    }
}
