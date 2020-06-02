package com.example.simplecrudddd.application.folder.strategy.createdocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.domain.DocumentType;

@Component
public class CreateDocumentFactory {

    private final Map<DocumentType, CreateDocumentStrategy> createStrategyByDocumentType;

    public CreateDocumentFactory(List<CreateDocumentStrategy> createStrategies) {
        createStrategyByDocumentType = new HashMap<>();
        createStrategies.forEach(createDocumentStrategy ->
                createStrategyByDocumentType.put(createDocumentStrategy.getDocumentType(), createDocumentStrategy));
    }

    public Optional<CreateDocumentStrategy> findCreateStrategyByDocumentType(DocumentType documentType) {
        return Optional.ofNullable(createStrategyByDocumentType.get(documentType));
    }
}
