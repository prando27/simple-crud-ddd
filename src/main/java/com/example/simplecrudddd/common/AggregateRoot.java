package com.example.simplecrudddd.common;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AggregateRoot extends Entity {

    @Version
    private Integer version;
}
