package com.increff.dto;

import com.increff.model.enums.ClientType;
import com.increff.model.form.OrderForm;
import com.increff.model.form.OrderItemForm;
import com.increff.pojo.OrderItemPojo;
import com.increff.pojo.OrderPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderForm orderForm) throws ApiException {
        //todo:: validate order form
        long clientId=clientService.getByName(orderForm.getClientName(), ClientType.CLIENT).getId();
        long customerId=clientService.getByName(orderForm.getCustomerName(),ClientType.CUSTOMER).getId();
        OrderPojo orderPojo=convertToOrderPOJO(orderForm,clientId,customerId); // TODO :: set internal channelid
        orderService.add(orderPojo);
        List<OrderItemPojo> orderItemPojoList=getOrderItemPojo(orderForm,orderPojo,clientId);
        orderItemService.add(orderItemPojoList);
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

    // todo:: make api to fetch all order and orderitems if required;
}
