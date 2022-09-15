package com.increff.dto;

import com.increff.form.ChannelOrderForm;
import com.increff.form.ChannelOrderUploadForm;
import com.increff.model.enums.ClientType;
import com.increff.model.enums.OrderStatus;
import com.increff.model.forms.OrderForm;
import com.increff.model.forms.OrderItemForm;
import com.increff.pojo.InventoryPojo;
import com.increff.pojo.OrderItemPojo;
import com.increff.pojo.OrderPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.Util.OrderUtil.validate;
import static com.increff.dto.DtoHelper.convert;
import static com.increff.dto.DtoHelper.convertToOrderPOJO;

@Repository
public class OrderDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
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

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderForm orderForm) throws ApiException {
        validate(orderForm);
        long clientId=clientService.getByName(orderForm.getClientName(), ClientType.CLIENT).getId();
        long customerId=clientService.getByName(orderForm.getCustomerName(),ClientType.CUSTOMER).getId();
        long channelId=channelService.getByName("INTERNAL").getId();
        OrderPojo orderPojo=convertToOrderPOJO(orderForm,clientId,customerId,channelId);
        orderService.add(orderPojo);
        List<OrderItemPojo> orderItemPojoList=getOrderItemPojo(orderForm,orderPojo,clientId);
        orderItemService.add(orderItemPojoList);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void allocate(long orderId) throws ApiException {
        OrderPojo orderPojo=orderService.getOrder(orderId);
        if(orderPojo.getStatus()!=OrderStatus.CREATED)
            throw new ApiException("Order already "+orderPojo.getStatus());
        List<OrderItemPojo> orderItemPojoList=orderItemService.getItemsByOrderId(orderId);
        boolean flag=true;
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            if(!Objects.equals(orderItemPojo.getOrderedQuantity(), orderItemPojo.getAllocatedQuantity())){
                flag=false;
                long quantityToAllocated=orderItemPojo.getOrderedQuantity()-orderItemPojo.getAllocatedQuantity();
                InventoryPojo inventoryPojo=inventoryService.check(orderItemPojo.getGlobalSkuId());
                long newAllocatedQuantity=Math.min(quantityToAllocated,inventoryPojo.getAvailableQuantity());
                orderItemService.allocateQuantity(orderItemPojo.getId(),newAllocatedQuantity);
                inventoryService.allocateInventory(orderItemPojo.getGlobalSkuId(),newAllocatedQuantity);
                binService.allocate(orderItemPojo.getGlobalSkuId(),newAllocatedQuantity);
            }
        }
        if(flag) orderService.updateOrderStatus(orderId);
    }

    private List<OrderItemPojo> getOrderItemPojo(OrderForm orderForm,OrderPojo orderPojo,long clientId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(OrderItemForm orderItemForm: orderForm.getOrderItems()){
            try {
                validate(orderItemForm);
                ProductPojo productPojo = productService.check(orderItemForm.getClientSkuId(), clientId);
                if(productPojo==null)
                    throw new ApiException("Invalid clientSkuId");
                orderItemPojoList.add(convert(orderPojo.getId(),productPojo.getGlobalSkuId(),orderItemForm));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return orderItemPojoList;
    }


    //todo:: don't touch as of now let it be here
    @Transactional(rollbackOn = ApiException.class)
    public void addChannelOrder(ChannelOrderUploadForm orderUploadForm) throws ApiException {
        validate(orderUploadForm);
        long channelId=channelService.getByName(orderUploadForm.getChannelName()).getId();
        clientService.checkIdAndType(orderUploadForm.getClientId(), ClientType.CLIENT);
        clientService.checkIdAndType(orderUploadForm.getCustomerId(), ClientType.CUSTOMER);
        OrderPojo orderPojo=convert(orderUploadForm,channelId);
        orderService.add(orderPojo);
        List<OrderItemPojo> orderItemPojoList=getOrderItemList(orderUploadForm,orderPojo.getId(),channelId);
        orderItemService.add(orderItemPojoList);
    }

    //todo :: don't touch
    private List<OrderItemPojo> getOrderItemList(ChannelOrderUploadForm orderUploadForm, long orderId, long channelId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(ChannelOrderForm orderForm: orderUploadForm.getItems()){
            try {
                long globalSkuID = channelListingService.getGlobalSkuId(orderForm.getChannelSkuId(), orderUploadForm.getClientId(), channelId);
                orderItemPojoList.add(DtoHelper.convert(orderId, orderForm, globalSkuID));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return orderItemPojoList;
    }

    public void generateInvoice(long orderId) throws ApiException {
        OrderPojo orderPojo=orderService.getOrder(orderId);

    }

    // todo:: make api to fetch all order and orderitems if required;
}
