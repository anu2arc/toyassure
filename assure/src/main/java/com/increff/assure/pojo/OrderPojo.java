package com.increff.assure.pojo;

import com.increff.commons.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class OrderPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;
    private OrderStatus status;
}
