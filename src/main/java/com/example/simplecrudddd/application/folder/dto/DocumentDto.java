package com.example.simplecrudddd.application.folder.dto;

import com.example.simplecrudddd.domain.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "documentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonalInfoDocumentDto.class, name = DocumentType.Constants.PERSONAL_INFO)}
)
public class DocumentDto {

    @JsonUnwrapped
    private final Object document;
}
