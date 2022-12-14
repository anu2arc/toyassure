package com.increff.assure.dto;

import com.increff.assure.client.ChannelClient;
import com.increff.assure.model.forms.OrderItemForm;
import com.increff.assure.pojo.*;
import com.increff.assure.service.*;
import com.increff.commons.data.InvoiceData;
import com.increff.commons.data.OrderData;
import com.increff.commons.data.OrderItemData;
import com.increff.commons.enums.ClientType;
import com.increff.commons.enums.InvoiceType;
import com.increff.commons.enums.OrderStatus;
import com.increff.commons.form.ItemList;
import com.increff.commons.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.assure.Util.OrderUtil.validate;
import static com.increff.assure.dto.DtoHelper.*;

@Service
public class OrderDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelListingService channelListingService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BinService binService;
    @Autowired
    private ChannelClient channelClient;
    @Transactional(rollbackOn = ApiException.class)
    public void add(com.increff.assure.model.forms.OrderForm orderForm) throws ApiException {
        validate(orderForm);
        long clientId= userService.getByName(orderForm.getClientName(), ClientType.CLIENT).getId();
        long customerId= userService.getByName(orderForm.getCustomerName(),ClientType.CUSTOMER).getId();
        long channelId=channelService.getByName("INTERNAL").getId();
        OrderPojo orderPojo= DtoHelper.convertToOrderPOJO(orderForm,clientId,customerId,channelId);
        orderService.add(orderPojo);
        List<OrderItemPojo> orderItemPojoList=getOrderItemPojo(orderForm,orderPojo,clientId);
        orderItemService.add(orderItemPojoList);
    }
    @Transactional(rollbackOn = ApiException.class)
    public void allocate(long orderId) throws ApiException {
        OrderPojo orderPojo=orderService.getOrder(orderId);
        if(!Objects.equals(orderPojo.getStatus(),OrderStatus.CREATED))
            throw new ApiException("Order already "+orderPojo.getStatus());
        List<OrderItemPojo> orderItemPojoList=orderItemService.getItemsByOrderId(orderId);
        boolean flag=true;
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            if(!Objects.equals(orderItemPojo.getOrderedQuantity(), orderItemPojo.getAllocatedQuantity())){
                long quantityToAllocated=orderItemPojo.getOrderedQuantity()-orderItemPojo.getAllocatedQuantity();
                InventoryPojo inventoryPojo=inventoryService.check(orderItemPojo.getGlobalSkuId());
                long newAllocatedQuantity=Math.min(quantityToAllocated,inventoryPojo.getAvailableQuantity());
                orderItemService.allocateQuantity(orderItemPojo.getId(),newAllocatedQuantity);
                inventoryService.allocateInventory(orderItemPojo.getGlobalSkuId(),newAllocatedQuantity);
                binService.allocate(orderItemPojo.getGlobalSkuId(),newAllocatedQuantity);
            }
            if(!orderItemService.getOrderStatus(orderItemPojo)) flag=false;
        }
        if(flag) orderService.updateOrderStatus(orderId,OrderStatus.ALLOCATED);
    }

    private List<OrderItemPojo> getOrderItemPojo(com.increff.assure.model.forms.OrderForm orderForm, OrderPojo orderPojo, long clientId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(OrderItemForm orderItemForm: orderForm.getOrderItems()){
            try {
                validate(orderItemForm);
                ProductPojo productPojo = productService.check(orderItemForm.getClientSkuId(), clientId);
                if(productPojo==null)
                    throw new ApiException("ClientSkuId does not exist");
                orderItemPojoList.add(DtoHelper.convertToOrderItemPOJO(orderPojo.getId(),productPojo.getGlobalSkuId(),orderItemForm));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return orderItemPojoList;
    }

    // order through channel comes here
    @Transactional(rollbackOn = ApiException.class)
    public String addChannelOrder(OrderForm orderUploadForm) throws ApiException {
        validate(orderUploadForm);
        long channelId=channelService.getByName(orderUploadForm.getChannelName()).getId();
        userService.checkIdAndType(orderUploadForm.getClientId(), ClientType.CLIENT);
        userService.checkIdAndType(orderUploadForm.getCustomerId(), ClientType.CUSTOMER);
        OrderPojo orderPojo= DtoHelper.convertOrderFormToOrderPojo(orderUploadForm,channelId);
        orderService.add(orderPojo);
        List<OrderItemPojo> orderItemPojoList=getOrderItemList(orderUploadForm,orderPojo.getId(),channelId);
        orderItemService.add(orderItemPojoList);
        return "Order placed with id:"+orderPojo.getId();
    }
    private List<OrderItemPojo> getOrderItemList(OrderForm orderUploadForm, long orderId, long channelId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(ItemList itemList : orderUploadForm.getItems()){
            try {
                long globalSkuID = channelListingService.getGlobalSkuId(itemList.getChannelSkuId(), orderUploadForm.getClientId(), channelId);
                orderItemPojoList.add(DtoHelper.convertOrderFormToOrderItemPojo(orderId, itemList, globalSkuID));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return orderItemPojoList;
    }

    @Transactional(rollbackOn = ApiException.class)
    public String generateInvoice(long orderId) throws ApiException, IOException, TransformerException {
        OrderPojo orderPojo=orderService.getOrder(orderId);
        if(Objects.equals(orderPojo.getStatus(),OrderStatus.CREATED))
            throw new ApiException("order not yet allocated");
        if(Objects.equals(orderPojo.getStatus(),OrderStatus.ALLOCATED)){
            fulfillOrder(orderId);
            orderService.updateOrderStatus(orderId,OrderStatus.FULFILLED);
        }
        if(Objects.equals(orderService.getOrder(orderId).getStatus(),OrderStatus.FULFILLED)){
            return generatePdf(orderPojo);
        }
        return "";
    }

    private String generatePdf(OrderPojo orderPojo) throws ApiException, IOException, TransformerException {
        String channelName=channelService.get(orderPojo.getChannelId()).getName();
        String customerName= userService.get(orderPojo.getCustomerId()).getName();
        String clientName= userService.get(orderPojo.getClientId()).getName();
        String invoiceType=channelService.get(orderPojo.getChannelId()).getInvoiceType().toString();
        ZonedDateTime orderTime=orderPojo.getCreated_at();
        List<OrderItemData> orderItemDataList=convertOrderItemPojoToOrderItemDataList(orderItemService.getItemsByOrderId(orderPojo.getId()));
        Double total=getTotalAmount(orderPojo.getId());
        InvoiceData invoiceData=new InvoiceData(orderItemDataList,total,channelName,customerName,invoiceType,clientName,orderTime);
        if(Objects.equals(channelService.get(orderPojo.getChannelId()).getInvoiceType(),InvoiceType.SELF))
            return orderService.generateInvoice(invoiceData,orderPojo.getId());
        else
            return channelClient.getInvoice(invoiceData);
    }

    private Double getTotalAmount(Long id) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=orderItemService.getItemsByOrderId(id);
        double total=0.0;
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            total+=orderItemPojo.getOrderedQuantity()*orderItemPojo.getSellingPricePerUnit();
        }
        return total;
    }

    private void fulfillOrder(long orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=orderItemService.getItemsByOrderId(orderId);
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            orderItemService.fulfillOrder(orderItemPojo.getId());
            inventoryService.fulfillOrder(orderItemPojo.getGlobalSkuId(),orderItemPojo.getOrderedQuantity());
        }
        orderService.updateOrderStatus(orderId,OrderStatus.FULFILLED);
    }

    public List<OrderData> getAll() throws ApiException {
        List<OrderPojo> orderPojoList=orderService.getAll();
        List<OrderData> orderDataList=new ArrayList<>();
        for(OrderPojo orderPojo:orderPojoList){
            String clientName= userService.get(orderPojo.getClientId()).getName();
            String customerName= userService.get(orderPojo.getCustomerId()).getName();
            String channelName=channelService.get(orderPojo.getChannelId()).getName();
            orderDataList.add(convertOrderPojoToOrderData(orderPojo,clientName,customerName,channelName));
        }
        return orderDataList;
    }

}
