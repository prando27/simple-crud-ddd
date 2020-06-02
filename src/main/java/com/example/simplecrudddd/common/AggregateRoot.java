package com.example.simplecrudddd.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import lombok.Getter;

@MappedSuperclass
public abstract class AggregateRoot extends Entity {

    @Getter
    @Transient
    private List<DomainEvent> domainEvents = new ArrayList<>();

    @Version
    private Integer version;

    public void registerEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }
}
