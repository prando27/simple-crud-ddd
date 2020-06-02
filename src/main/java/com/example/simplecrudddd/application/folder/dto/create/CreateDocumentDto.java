package com.example.simplecrudddd.application.folder.dto.create;

import com.example.simplecrudddd.domain.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "documentType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreatePersonalInfoDocumentDto.class, name = DocumentType.Constants.PERSONAL_INFO),

        @JsonSubTypes.Type(value = CreateAddressDocumentDto.class, name = DocumentType.Constants.ADDRESS),

        @JsonSubTypes.Type(value = CreateCnhDocumentDto.class, name = DocumentType.Constants.CNH),

        @JsonSubTypes.Type(value = CreateRgDocumentDto.class, name = DocumentType.Constants.RG)}
)
public abstract class CreateDocumentDto {

    private DocumentType documentType;
}
