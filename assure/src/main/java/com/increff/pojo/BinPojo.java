package com.increff.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class BinPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long binId;
}
