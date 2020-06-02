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

        // TODO - Pesquisar sobre o método Result.combine que faz um merge de vários results para determinar o isError
        Result<Name> fullNameResult = Name.create(dto.getFullName());
        if (fullNameResult.isError()) {
            return Result.error(fullNameResult.getError());
        }
        existingPersonalInfoDocument.changeFullName(fullNameResult.getValue());

        // TODO - Pensar sobre cenários onde em um contexto o dado vem e em outro não
        if (dto.getCpf() != null) {
            Result<Cpf> cpfResult = Cpf.create(dto.getCpf());
            if (cpfResult.isError()) {
                return Result.error(cpfResult.getError());
            }
            existingPersonalInfoDocument.changeCpf(cpfResult.getValue());
        }

        Result<Email> emailResult = Email.create(dto.getEmail());
        if (emailResult.isError()) {
            return Result.error(emailResult.getError());
        }
        existingPersonalInfoDocument.changeEmail(emailResult.getValue());

        return Result.ok(existingPersonalInfoDocument);
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
