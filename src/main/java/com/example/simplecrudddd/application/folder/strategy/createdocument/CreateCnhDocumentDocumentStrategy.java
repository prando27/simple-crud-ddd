package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.dto.CreateCnhDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.CnhDocument;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CreateCnhDocumentDocumentStrategy implements CreateDocumentStrategy {

    private final DocumentCopyApplicationService documentCopyApplicationService;

    @Override
    public Result<CnhDocument> create(CreateDocument createDocument) {
        var createCnhDocumentDto = (CreateCnhDocumentDto) createDocument.getCreateDocumentDto();

        var documentCopyOptional = documentCopyApplicationService
                .findDocumentCopy(
                        createDocument.getFolderId(),
                        createCnhDocumentDto.getFileName());

        if (documentCopyOptional.isEmpty()) {
            return Result.error("FileName not exists, upload a document copy first");
        }

        return CnhDocument.create(createCnhDocumentDto.getFileName());
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.CNH;
    }
}
