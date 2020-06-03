package com.example.simplecrudddd.application.folder.strategy.createdocument;

import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;
import com.example.simplecrudddd.domain.folder.entity.document.Document;

public interface CreateDocumentStrategy {

    Result<? extends Document> create(CreateDocumentStrategyInput input);

    DocumentType getApplicableDocumentType();
}
