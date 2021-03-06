package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.folder.dto.create.CreateAddressDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;
import com.example.simplecrudddd.domain.folder.entity.document.AddressDocument;

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
