package com.example.simplecrudddd.domain.folder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.example.simplecrudddd.common.AggregateRoot;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.folder.document.Document;
import com.example.simplecrudddd.domain.folder.document.DocumentTypeLimitPerFolder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Folder extends AggregateRoot {

    private Long userId;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Document> documents = new ArrayList<>();

    public boolean addDocument(Document document) {
        // horrivel isso aqui, melhorar...
        if (document.getDocumentTypeLimitPerFolder() == DocumentTypeLimitPerFolder.ONE) {
            if (isDocumentAlreadyStoredByDocumentType(document.getDocumentType())) {
                return false;
            }
        } else {
            if (isDocumentWithSameContentExists(document.getContent())) {
                return false;
            }
        }

//        registerEvent(new DocumentCreatedEvent());

        document.setFolder(this);
        return documents.add(document);
    }

//    public Optional<Document> findIdentityDocument() {
//        return documents.stream()
//                .filter(document -> document instanceof IdentityDocument)
//                .findFirst();
//    }

    private boolean isDocumentWithSameContentExists(String documentContent) {
        return documents.stream()
                .map(Document::getContent)
                .anyMatch(content -> content.equals(documentContent));
    }

    private boolean isDocumentAlreadyStoredByDocumentType(DocumentType documentType) {
        return documents.stream()
                .map(Document::getDocumentType)
                .anyMatch(documentType::equals);
    }

    public Optional<Document> findDocumentByDocumentTypeLimitPerFolder(Document document) {
        if (document.getDocumentTypeLimitPerFolder() == DocumentTypeLimitPerFolder.ONE) {
            return findDocumentByDocumentType(document.getDocumentType());
        }

        return findDocumentByContent(document.getContent());
    }

    public Optional<Document> findDocumentById(Long documentId) {
        return documents.stream().filter(document -> document.getId().equals(documentId)).findFirst();
    }

    private Optional<Document> findDocumentByDocumentType(DocumentType documentType) {
        return documents.stream()
                .filter(document -> documentType.equals(document.getDocumentType()))
                .findFirst();
    }

    private Optional<Document> findDocumentByContent(String content) {
        return documents.stream()
                .filter(document -> content.equals(document.getContent()))
                .findFirst();
    }

    public static Folder create(Long userId) {
        return new Folder(userId, null);
    }
}
