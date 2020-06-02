package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.dto.CreateAddressDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.AddressDocument;

@Component
public class AddressCreateDocumentStrategy implements CreateDocumentStrategy {

    @Override
    public Result<AddressDocument> create(CreateDocumentStrategyInput input) {
        var createAddressDocumentDto = (CreateAddressDocumentDto) input.getCreateDocumentDto();

        return AddressDocument.create(createAddressDocumentDto.getStreet());
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.ADDRESS;
    }
}
