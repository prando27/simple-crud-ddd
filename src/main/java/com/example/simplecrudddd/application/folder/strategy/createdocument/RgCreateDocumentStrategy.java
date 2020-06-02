package com.example.simplecrudddd.application.folder.strategy.createdocument;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.dto.CreateRgDocumentDto;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.RgDocument;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RgCreateDocumentStrategy implements CreateDocumentStrategy {

    private final DocumentCopyApplicationService documentCopyApplicationService;

    @Override
    public Result<RgDocument> create(CreateDocument createDocument) {
        var createRgDocumentDto = (CreateRgDocumentDto) createDocument.getCreateDocumentDto();

        var documentCopyOptional = documentCopyApplicationService
                .findDocumentCopy(
                        createDocument.getFolderId(),
                        createRgDocumentDto.getFileName());

        if (documentCopyOptional.isEmpty()) {
            return Result.error("FileName not exists, upload a document copy first");
        }

        return RgDocument.create(createRgDocumentDto.getFileName());
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.RG;
    }
}
