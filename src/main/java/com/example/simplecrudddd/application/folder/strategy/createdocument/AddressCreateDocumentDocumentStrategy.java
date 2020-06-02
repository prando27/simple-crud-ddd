package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.dto.CreateAddressDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.AddressDocument;

@Component
public class AddressCreateDocumentDocumentStrategy implements CreateDocumentStrategy {

    @Override
    public Result<AddressDocument> create(CreateDocument createDocument) {
        var createAddressDocumentDto = (CreateAddressDocumentDto) createDocument.getCreateDocumentDto();

        return AddressDocument.create(createAddressDocumentDto.getStreet());
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.ADDRESS;
    }
}
