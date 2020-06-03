package com.example.simplecrudddd.infrastructure.config.objectmapper.mixin;

import com.example.simplecrudddd.domain.folder.entity.document.DocumentTypeLimitPerFolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class DocumentMixIn {

    @JsonIgnore
    abstract DocumentTypeLimitPerFolder getDocumentTypeLimitPerFolder();

    @JsonIgnore
    abstract String getContent();
}
