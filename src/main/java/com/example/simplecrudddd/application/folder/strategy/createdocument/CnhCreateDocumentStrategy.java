package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.folder.dto.create.CreateCnhDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;
import com.example.simplecrudddd.domain.folder.entity.document.CnhDocument;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CnhCreateDocumentStrategy implements CreateDocumentStrategy {

    private final DocumentCopyApplicationService documentCopyApplicationService;

    @Override
    public Result<CnhDocument> create(CreateDocumentStrategyInput input) {
        var dto = (CreateCnhDocumentDto) input.getCreateDocumentDto();

        var documentCopyOptional = documentCopyApplicationService
                .findDocumentCopy(
                        input.getFolderId(),
                        dto.getFileName());

        if (documentCopyOptional.isEmpty()) {
            return Result.error("FileName not exists, upload a document copy first");
        }

        return CnhDocument.create(dto.getFileName());
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.CNH;
    }
}
