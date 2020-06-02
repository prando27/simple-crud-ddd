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
@DiscriminatorValue(DocumentType.Constants.ADDRESS)
public class AddressDocument extends Document {

    //================================================================================
    // Accessors
    //================================================================================

    public String getStreet() {
        return attributes.getStreet();
    }

    @Override
    public DocumentTypeLimitPerFolder getDocumentTypeLimitPerFolder() {
        return DocumentTypeLimitPerFolder.MANY;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.ADDRESS;
    }

    @Override
    public String getContent() {
        return getDocumentType()
                + getStreet();
    }

    //================================================================================
    // Static Factory Methods
    //================================================================================

    public static Result<AddressDocument> create(
            String street) {

        if (street == null || street.length() == 0) {
            return Result.error("Street cannot be null or empty");
        }

        return Result.ok(new AddressDocument(new AddressAttributes(
                null, null, null, null, null, null
        )));
    }

    //================================================================================
    // JPA/Hibernate persistence value objects and attributes
    //================================================================================

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Getter(AccessLevel.PRIVATE)
    private AddressAttributes attributes;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    static class AddressAttributes {

        private String cep;
        private String city;
        private String state;
        private String neighborhood;
        private String street;
        private String number;
    }
}
