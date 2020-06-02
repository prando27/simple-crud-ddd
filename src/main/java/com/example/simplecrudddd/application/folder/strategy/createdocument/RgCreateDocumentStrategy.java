package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.folder.dto.create.CreateRgDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.RgDocument;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RgCreateDocumentStrategy implements CreateDocumentStrategy {

    private final DocumentCopyApplicationService documentCopyApplicationService;

    @Override
    public Result<RgDocument> create(CreateDocumentStrategyInput input) {
        var dto = (CreateRgDocumentDto) input.getCreateDocumentDto();

        var documentCopyOptional = documentCopyApplicationService
                .findDocumentCopy(
                        input.getFolderId(),
                        dto.getFileName());

        if (documentCopyOptional.isEmpty()) {
            return Result.error("FileName not exists, upload a document copy first");
        }

        return RgDocument.create(dto.getFileName());
    }

    @Override
    public DocumentType getApplicableDocumentType() {
        return DocumentType.RG;
    }
}
