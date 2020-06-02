package com.example.simplecrudddd.application.dto;

import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "documentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreatePersonalInfoDocumentDto.class, name = DocumentType.Constants.PERSONAL_INFO),

        @JsonSubTypes.Type(value = CreateAddressDocumentDto.class, name = DocumentType.Constants.ADDRESS) }
)
@NoArgsConstructor
public abstract class UpdateDocumentDto {

    private String documentType;

    public abstract Result<? extends Document> updateDocument(Document document);
}
