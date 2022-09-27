package com.increff.assure.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"binId","globalSkuId"})})
public class BinSkuPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false)
    private Long binId;
    @Column(nullable = false)
    private Long globalSkuId;
    @Column(nullable = false)
    private Long quantity;
}
