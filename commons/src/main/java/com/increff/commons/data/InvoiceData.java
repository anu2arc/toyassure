package com.increff.commons.data;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@XmlRootElement
public class InvoiceData {
    private Double total;
    private String invoiceTime;
    private String channelName;
    private String customerName;
    private List<OrderItemData> orderItems;

    public InvoiceData(){}

    public InvoiceData(List<OrderItemData> orderItemDataList,Double total,String channelName,String customerName){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.invoiceTime= dtf.format(ZonedDateTime.now());
        this.orderItems=orderItemDataList;
        this.total=total;
        this.channelName=channelName;
        this.customerName=customerName;
    }
}
