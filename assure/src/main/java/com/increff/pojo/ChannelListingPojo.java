package com.increff.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"channelId","channelSkuId","globalSkuId"})})
public class ChannelListingPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Long channelId;
    private String channelSkuId;
    private Long clientId;
    private Long globalSkuId;
}
