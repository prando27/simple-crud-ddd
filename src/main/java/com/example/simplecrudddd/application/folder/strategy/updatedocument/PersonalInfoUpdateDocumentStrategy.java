package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.folder.dto.update.UpdatePersonalInfoDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.Email;
import com.example.simplecrudddd.domain.Name;
import com.example.simplecrudddd.domain.folder.document.PersonalInfoDocument;

@Component
public class PersonalInfoUpdateDocumentStrategy implements UpdateDocumentStrategy {

    @Override
    public Result<PersonalInfoDocument> update(UpdateDocumentStrategyInput input) {
        var dto = (UpdatePersonalInfoDocumentDto) input.getDto();
        var existingPersonalInfoDocument = (PersonalInfoDocument) input.getExistingDocument();

        // TODO - Search about the Result.combine method
        Result<Name> fullNameResult = Name.create(dto.getFullName());
        if (fullNameResult.isError()) {
            return Result.error(fullNameResult.getError());
        }

        Result<Cpf> cpfResult = Cpf.create(dto.getCpf());
        if (cpfResult.isError()) {
            return Result.error(cpfResult.getError());
        }

        Result<Email> emailResult = Email.create(dto.getEmail());
        if (emailResult.isError()) {
            return Result.error(emailResult.getError());
        }

        existingPersonalInfoDocument.update(
                fullNameResult.getValue(),
                cpfResult.getValue(),
                emailResult.getValue()
        );

        return Result.ok(existingPersonalInfoDocument);
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
