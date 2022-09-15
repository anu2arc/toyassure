package com.increff.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChannelOrderUploadForm {
    private String channelName;
    private Long clientId;
    private Long customerId;
    private String channelOrderId;
    private List<ChannelOrderForm> items;
}
