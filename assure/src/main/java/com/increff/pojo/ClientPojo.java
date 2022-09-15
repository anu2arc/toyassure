package com.increff.pojo;

import com.increff.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name","clientType"})})
public class ClientPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ClientType clientType;
}
