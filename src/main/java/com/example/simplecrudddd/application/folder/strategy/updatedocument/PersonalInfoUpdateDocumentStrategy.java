package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.dto.UpdateDocumentDto;
import com.example.simplecrudddd.application.dto.UpdatePersonalInfoDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.Name;
import com.example.simplecrudddd.domain.folder.document.Document;
import com.example.simplecrudddd.domain.folder.document.PersonalInfoDocument;

@Component
public class PersonalInfoUpdateDocumentStrategy implements UpdateDocumentStrategy {

    @Override
    public Result<PersonalInfoDocument> update(UpdateDocumentDto dto, Document document) {
        var updatePersonalInfoDocumentDto = (UpdatePersonalInfoDocumentDto) dto;
        var personalInfoDocument = (PersonalInfoDocument) document;

        Result<Name> nameResult = Name.create(updatePersonalInfoDocumentDto.getFullName());
        if (nameResult.isError()) {
            return Result.error(nameResult.getError());
        }

        Result<Cpf> cpfResult = Cpf.create(updatePersonalInfoDocumentDto.getCpf());
        if (cpfResult.isError()) {
            return Result.error(cpfResult.getError());
        }

        personalInfoDocument.update(
                nameResult.getValue(),
                cpfResult.getValue(),
                updatePersonalInfoDocumentDto.getEmail()
        );

        return Result.ok(personalInfoDocument);
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
