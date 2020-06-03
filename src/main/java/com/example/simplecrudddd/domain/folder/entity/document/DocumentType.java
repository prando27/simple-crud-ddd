package com.example.simplecrudddd.domain.folder.entity.document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DocumentType {

    @Id
    private Long id;

    private String name;
}
