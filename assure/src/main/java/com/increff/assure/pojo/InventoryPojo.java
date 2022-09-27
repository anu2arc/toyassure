package com.increff.assure.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class InventoryPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false,unique = true)
    private Long globalSkuId;
    @Column(nullable = false)
    private Long availableQuantity;
    @Column(nullable = false)
    private Long allocatedQuantity;
    @Column(nullable = false)
    private Long fulfilledQuantity;
}
