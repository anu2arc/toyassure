package com.increff.form;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelOrderForm {
    private String channelSkuId;
    private Long quantity;
    private Double sellingPrice;
}
