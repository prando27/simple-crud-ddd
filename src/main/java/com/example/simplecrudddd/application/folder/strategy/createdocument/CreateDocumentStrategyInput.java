package com.example.simplecrudddd.application.folder.strategy.createdocument;

import com.example.simplecrudddd.application.folder.dto.create.CreateDocumentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateDocumentStrategyInput {

    private final Long folderId;

    private final CreateDocumentDto createDocumentDto;
}
