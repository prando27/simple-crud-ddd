package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.domain.DocumentType;

@Component
public class UpdateDocumentStrategyFactory {

    private final Map<DocumentType, UpdateDocumentStrategy> updateStrategyByDocumentType;

    public UpdateDocumentStrategyFactory(List<UpdateDocumentStrategy> updateStrategies) {
        updateStrategyByDocumentType = new HashMap<>();
        updateStrategies.forEach(updateDocumentStrategy ->
                updateStrategyByDocumentType.put(updateDocumentStrategy.getDocumentType(), updateDocumentStrategy));
    }

    public Optional<UpdateDocumentStrategy> findCreateStrategyByDocumentType(DocumentType documentType) {
        return Optional.ofNullable(updateStrategyByDocumentType.get(documentType));
    }
}
