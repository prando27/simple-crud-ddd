package com.example.simplecrudddd.application.folder.strategy.createdocument;

import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.Document;

public interface CreateDocumentStrategy {

    Result<? extends Document> create(CreateDocument create);

    DocumentType getDocumentType();
}
