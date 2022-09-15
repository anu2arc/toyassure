package com.increff.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public class AbstractPojo {
    @Version
    private Integer version;
    @CreationTimestamp
    private ZonedDateTime created_at;
    @UpdateTimestamp
    private ZonedDateTime updated_at;

}
