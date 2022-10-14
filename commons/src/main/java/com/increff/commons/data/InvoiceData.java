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
    private String time;
    private String invoiceTime;
    private String clientName;
    private String invoiceType;
    private String channelName;
    private String customerName;
    private List<OrderItemData> orderItems;
    public InvoiceData(){}
    public InvoiceData(List<OrderItemData> orderItemDataList,Double total,String channelName,String customerName,String invoiceType,String clientName,ZonedDateTime orderTime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.time=dtf.format(orderTime);
        this.invoiceTime= dtf.format(ZonedDateTime.now());
        this.orderItems=orderItemDataList;
        this.total=total;
        this.channelName=channelName;
        this.customerName=customerName;
        this.invoiceType=invoiceType;
        this.clientName=clientName;
    }
}
