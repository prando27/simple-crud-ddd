package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.dto.UpdateCnhDocumentDto;
import com.example.simplecrudddd.application.dto.UpdateDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.CnhDocument;
import com.example.simplecrudddd.domain.folder.document.Document;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CnhUpdateDocumentStrategy implements UpdateDocumentStrategy {

    private final DocumentCopyApplicationService documentCopyApplicationService;

    @Override
    public Result<CnhDocument> update(UpdateDocumentDto dto, Document document) {
        var updateCnhDocumentDto = (UpdateCnhDocumentDto) dto;
        var cnhDocument = (CnhDocument) document;

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
    public DocumentType getDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }
}
