package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import com.example.simplecrudddd.application.dto.UpdateDocumentDto;
import com.example.simplecrudddd.domain.folder.document.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateDocumentStrategyInput {

    private final UpdateDocumentDto dto;

    private final Document existingDocument;
}
