package com.example.simplecrudddd.domain.folder.entity.document;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.entity.Folder;

import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type")
public abstract class Document extends com.example.simplecrudddd.common.Entity {

    @Column(name = "document_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Setter
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public abstract DocumentTypeLimitPerFolder getDocumentTypeLimitPerFolder();

    public abstract DocumentType getDocumentType();

    public abstract String getContent();
}
