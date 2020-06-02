package com.example.simplecrudddd.domain.folder.document;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.DocumentType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@DiscriminatorValue(DocumentType.Constants.CNH)
public class CnhDocument extends Document implements IdentityDocument {

    public String getFileName() {
        return attributes.getFileName();
    }

    public static Result<CnhDocument> create(
            String fileName) {

        if (fileName == null || fileName.length() == 0) {
            return Result.error("FileName cannot be null or empty");
        }

        return Result.ok(new CnhDocument(new CnhAttributes(fileName)));
    }

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Getter(AccessLevel.PRIVATE)
    private CnhAttributes attributes;

    @Override
    public DocumentTypeLimitPerFolder getDocumentTypeLimitPerFolder() {
        return DocumentTypeLimitPerFolder.ONE;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.CNH;
    }

    @Override
    public String getContent() {
        return getDocumentType() + getFileName();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    static class CnhAttributes {

        private String fileName;
    }

}
