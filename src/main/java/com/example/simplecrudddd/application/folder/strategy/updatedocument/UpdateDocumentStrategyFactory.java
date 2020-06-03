package com.example.simplecrudddd.application.folder.strategy.updatedocument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.simplecrudddd.domain.folder.entity.document.DocumentType;

@Component
public class UpdateDocumentStrategyFactory {

    private final Map<DocumentType, UpdateDocumentStrategy> updateStrategyByDocumentType;

    private UpdateDocumentStrategyFactory(List<UpdateDocumentStrategy> updateStrategies) {
        updateStrategyByDocumentType = new HashMap<>();

        updateStrategies.forEach(updateDocumentStrategy ->
                updateStrategyByDocumentType.put(
                        updateDocumentStrategy.getApplicableDocumentType(),
                        updateDocumentStrategy));
    }

    public Optional<UpdateDocumentStrategy> findUpdateStrategyByDocumentType(DocumentType documentType) {
        return Optional.ofNullable(updateStrategyByDocumentType.get(documentType));
    }
}
