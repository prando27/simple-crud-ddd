package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.dto.CreatePersonalInfoDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.Name;
import com.example.simplecrudddd.domain.folder.document.PersonalInfoDocument;

@Component
public class PersonalInfoCreateDocumentDocumentStrategy implements CreateDocumentStrategy {

    @Override
    public Result<PersonalInfoDocument> create(CreateDocument createDocument) {
        CreatePersonalInfoDocumentDto personalDto = (CreatePersonalInfoDocumentDto) createDocument.getCreateDocumentDto();

        Result<Name> nameResult = Name.create(personalDto.getFullName());
        if (nameResult.isError()) {
            return Result.error(nameResult.getError());
        }

        Result<Cpf> cpfResult = Cpf.create(personalDto.getCpf());
        if (cpfResult.isError()) {
            return Result.error(cpfResult.getError());
        }

        return Result.ok(PersonalInfoDocument.create(
                nameResult.getValue(),
                cpfResult.getValue(),
                personalDto.getEmail()
        ));
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
