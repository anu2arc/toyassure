package com.increff.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"clientSkuId","clientId"})})
public class ProductPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long globalSkuId;
    @Column(nullable = false)
    private String clientSkuId;
    @Column(nullable = false)
    private Long clientId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String brandId;
    @Column(nullable = false)
    private Double mrp;
    @Column(nullable = false)
    private String description;
}
