package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;
import com.example.simplecrudddd.domain.folder.entity.document.CnhDocument;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CnhUpdateDocumentStrategy implements UpdateDocumentStrategy {

    private final DocumentCopyApplicationService documentCopyApplicationService;

    @Override
    public Result<CnhDocument> update(UpdateDocumentStrategyInput input) {
//        var updateCnhDocumentDto = (UpdateCnhDocumentDto) dto;
//        var cnhDocument = (CnhDocument) document;

//        documentCopyApplicationService.findDocumentCopy()

//        personalInfoDocument.update(
//                nameResult.getValue(),
//                cpfResult.getValue(),
//                updatePersonalInfoDocumentDto.getEmail()
//        );
//
//        return Result.ok(personalInfoDocument);
        return null;
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
