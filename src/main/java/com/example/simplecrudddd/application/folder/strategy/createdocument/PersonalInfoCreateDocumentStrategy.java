package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.folder.dto.create.CreatePersonalInfoDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;
import com.example.simplecrudddd.domain.Email;
import com.example.simplecrudddd.domain.Name;
import com.example.simplecrudddd.domain.folder.entity.document.PersonalInfoDocument;

@Component
public class PersonalInfoCreateDocumentStrategy implements CreateDocumentStrategy {

    @Override
    public Result<PersonalInfoDocument> create(CreateDocumentStrategyInput input) {
        CreatePersonalInfoDocumentDto dto = (CreatePersonalInfoDocumentDto) input.getCreateDocumentDto();

        Result<Name> nameResult = Name.create(dto.getFullName());
        if (nameResult.isError()) {
            return Result.error(nameResult.getError());
        }

        Result<Cpf> cpfResult = Cpf.create(dto.getCpf());
        if (cpfResult.isError()) {
            return Result.error(cpfResult.getError());
        }

        Result<Email> emailResult = Email.create(dto.getEmail());
        if (emailResult.isError()) {
            return Result.error(emailResult.getError());
        }

        return Result.ok(PersonalInfoDocument.create(
                nameResult.getValue(),
                cpfResult.getValue(),
                emailResult.getValue()
        ));
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
