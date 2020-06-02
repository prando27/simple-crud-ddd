package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.Document;

public interface UpdateDocumentStrategy {

    Result<? extends Document> update(UpdateDocumentStrategyInput input);

    DocumentType getApplicableDocumentType();
}
