package com.increff.commons.form;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelOrderForm {//todo rename
    private String channelSkuId;
    private Long quantity;
    private Double sellingPrice;
}