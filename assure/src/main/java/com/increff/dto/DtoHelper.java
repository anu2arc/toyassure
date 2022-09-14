package com.increff.dto;

import com.increff.model.data.*;
import com.increff.model.enums.ClientType;
import com.increff.model.enums.InvoiceType;
import com.increff.model.form.*;
import com.increff.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.model.enums.OrderStatus.*;

@Repository
public class DtoHelper {
    public static ClientData convert(ClientPojo userPojo){
        ClientData clientData =new ClientData();
        clientData.setName(userPojo.getName());
        clientData.setType(userPojo.getClientType());
        return clientData;
    }
    public static ClientPojo convert(ClientForm clientForm){
        ClientPojo userPojo=new ClientPojo();
        userPojo.setClientType(ClientType.valueOf(clientForm.getClientType()));
        userPojo.setName(clientForm.getName());
        return userPojo;
    }
    public static ProductData convert(ProductPojo productPojo){
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
    public static ProductPojo convert(ProductForm productForm,long userID){
        ProductPojo productPojo=new ProductPojo();
        productPojo.setClientSkuId(productForm.getClientSkuId());
        productPojo.setClientId(userID);
        productPojo.setName(productForm.getName());
        productPojo.setBrandId(productForm.getBrandId());
        productPojo.setMrp(productForm.getMrp());
        productPojo.setDescription(productForm.getDescription());
        return productPojo;
    }

    public static ProductPojo convert(ProductUpdateForm productUpdateForm,long gSkuId){
        ProductPojo productPojo=new ProductPojo();
        productPojo.setGlobalSkuId(gSkuId);
        productPojo.setName(productUpdateForm.getName());
        productPojo.setBrandId(productUpdateForm.getBrandId());
        productPojo.setMrp(productUpdateForm.getMrp());
        productPojo.setDescription(productUpdateForm.getDescription());
        return productPojo;
    }
    public static BinData convert(BinPojo binPojo){
        BinData binData=new BinData();
        binData.setBinId(binPojo.getBinId());
        return binData;
    }
    public static List<InventoryPojo> convertx(List<BinSkuPojo> binSkuPojoList){
        List<InventoryPojo> inventoryPojoList=new ArrayList<>();
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            inventoryPojoList.add(create(binSkuPojo));
        }
        return inventoryPojoList;
    }
    public static BinSkuPojo convert(BinSkuForm binSkuForm,long globalSkuId){
        BinSkuPojo binSkuPojo=new BinSkuPojo();
        binSkuPojo.setBinId(binSkuForm.getBinId());
        binSkuPojo.setGlobalSkuId(globalSkuId);
        binSkuPojo.setQuantity(binSkuForm.getQuantity());
        return binSkuPojo;
    }
    public static BinSkuData convert(BinSkuPojo binSkuPojo){
        BinSkuData binSkuData=new BinSkuData();
        binSkuData.setId(binSkuPojo.getId());
        binSkuData.setBinId(binSkuPojo.getBinId());
        binSkuData.setGlobalSkuId(binSkuPojo.getGlobalSkuId());
        binSkuData.setQuantity(binSkuPojo.getQuantity());
        return binSkuData;
    }

    public static ChannelPojo convert(ChannelForm channelForm){
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
    public static ChannelListingPojo convert(Long channelId, String channelSkuId, Long clientId, Long globalSkuId) {
        ChannelListingPojo channelListingPojo=new ChannelListingPojo();
        channelListingPojo.setChannelId(channelId);
        channelListingPojo.setChannelSkuId(channelSkuId);
        channelListingPojo.setClientId(clientId);
        channelListingPojo.setGlobalSkuId(globalSkuId);
        return channelListingPojo;
    }
    public static List<ChannelListingData> convert(List<ChannelListingPojo> channelListingPojoList){
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

    public static OrderItemPojo convert(Long orderId, Long globalSkuId, OrderItemForm orderItemForm) {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setGlobalSkuId(globalSkuId);
        orderItemPojo.setOrderedQuantity(orderItemForm.getOrderedQuantity());
        orderItemPojo.setAllocatedQuantity(0L);
        orderItemPojo.setFulfilledQuantity(0L);
        orderItemPojo.setSellingPricePerUnit(orderItemForm.getSellingPricePerUnit());
        return orderItemPojo;
    }
}
