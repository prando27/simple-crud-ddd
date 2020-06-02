package com.example.simplecrudddd.application.folder.dto.update;

import com.example.simplecrudddd.domain.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "documentType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdatePersonalInfoDocumentDto.class, name = DocumentType.Constants.PERSONAL_INFO),

        @JsonSubTypes.Type(value = UpdateCnhDocumentDto.class, name = DocumentType.Constants.CNH)}
)
public abstract class UpdateDocumentDto {

    private DocumentType documentType;
}
