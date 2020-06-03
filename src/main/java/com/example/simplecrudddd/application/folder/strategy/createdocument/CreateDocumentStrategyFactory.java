package com.example.simplecrudddd.application.folder.strategy.createdocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;

@Component
public class CreateDocumentStrategyFactory {

    private final Map<DocumentType, CreateDocumentStrategy> createStrategyByDocumentType;

    private CreateDocumentStrategyFactory(List<CreateDocumentStrategy> createStrategies) {
        createStrategyByDocumentType = new HashMap<>();

        createStrategies.forEach(createDocumentStrategy ->
                createStrategyByDocumentType.put(
                        createDocumentStrategy.getApplicableDocumentType(),
                        createDocumentStrategy));
    }

    public Optional<CreateDocumentStrategy> findCreateStrategyByDocumentType(DocumentType documentType) {
        return Optional.ofNullable(createStrategyByDocumentType.get(documentType));
    }
}
