package com.example.simplecrudddd.domain.folder.entity.document;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import com.example.simplecrudddd.common.Result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@DiscriminatorValue(DocumentType.Constants.RG)
public class RgDocument extends Document implements IdentityDocument {

    //================================================================================
    // Accessors
    //================================================================================

    public String getFileName() {
        return attributes.getFileName();
    }

    @Override
    public DocumentTypeLimitPerFolder getDocumentTypeLimitPerFolder() {
        return DocumentTypeLimitPerFolder.ONE;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.RG;
    }

    @Override
    public String getContent() {
        return getDocumentType() + getFileName();
    }

    //================================================================================
    // Static Factory Methods
    //================================================================================

    public static Result<RgDocument> create(
            String fileName) {

        if (fileName == null || fileName.length() == 0) {
            return Result.error("FileName cannot be null or empty");
        }

        return Result.ok(new RgDocument(new RgAttributes(fileName)));
    }

    //================================================================================
    // JPA/Hibernate persistence value objects and attributes
    //================================================================================

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Getter(AccessLevel.PRIVATE)
    private RgAttributes attributes;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    static class RgAttributes {

        private String fileName;
    }

}
