package com.increff.dto;

import com.increff.model.form.OrderForm;
import com.increff.model.form.OrderItemForm;
import com.increff.pojo.OrderItemPojo;
import com.increff.pojo.OrderPojo;
import com.increff.pojo.ProductPojo;
import com.increff.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Transactional
    public void add(OrderForm orderForm) throws ApiException {
        clientService.get(orderForm.getClientId());
        try{
            clientService.get(orderForm.getCustomerId());}
        catch (ApiException exception){
            throw new ApiException("Invalid customerId");
        }
        OrderPojo orderPojo=convertToOrderPOJO(orderForm); // TODO :: set internal channelid
        orderService.add(orderPojo);
        List<OrderItemPojo> orderItemPojoList=getOrderItemPojo(orderForm,orderPojo);
        orderItemService.add(orderItemPojoList);
    }

    private List<OrderItemPojo> getOrderItemPojo(OrderForm orderForm,OrderPojo orderPojo) throws ApiException {
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        StringBuilder error=new StringBuilder();
        for(OrderItemForm orderItemForm: orderForm.getOrderItems()){
            try {
                validate(orderItemForm);
                ProductPojo productPojo = productService.check(orderItemForm.getClientSkuId(), orderForm.getClientId());
                if(productPojo==null)
                    throw new ApiException("Invalid clientId and clientSkuId pair");
                orderItemPojoList.add(convert(orderPojo.getId(),productPojo.getGlobalSkuId(),orderItemForm));
            } catch (ApiException exception){
                error.append(exception.getMessage());
            }
        }
        if(!error.toString().isEmpty())
            throw new ApiException(error.toString());
        return orderItemPojoList;
    }
}
