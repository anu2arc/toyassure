package com.increff.pojo;

import com.increff.model.enums.InvoiceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ChannelPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private InvoiceType invoiceType;
}
