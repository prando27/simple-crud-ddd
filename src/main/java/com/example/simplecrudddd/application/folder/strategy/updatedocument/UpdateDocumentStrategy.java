package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;
import com.example.simplecrudddd.domain.folder.entity.document.Document;

public interface UpdateDocumentStrategy {

    Result<? extends Document> update(UpdateDocumentStrategyInput input);

    DocumentType getApplicableDocumentType();
}
