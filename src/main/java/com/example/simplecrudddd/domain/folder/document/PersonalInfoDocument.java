package com.example.simplecrudddd.domain.folder.document;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.Name;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@DiscriminatorValue(DocumentType.Constants.PERSONAL_INFO)
public class PersonalInfoDocument extends Document {

    public Name getFullName() {
        return new Name(attributes.getFullName());
    }

    public Cpf getCpf() {
        return new Cpf(attributes.getCpf());
    }

    public String getEmail() {
        return attributes.getEmail();
    }

    public static PersonalInfoDocument create(
            Name fullName,
            Cpf cpf,
            String email) {
        return new PersonalInfoDocument(new PersonalInfoAttributes(fullName.getValue(), cpf.getValue(), email));
    }

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Getter(AccessLevel.PRIVATE)
    private PersonalInfoAttributes attributes;

    @Override
    public DocumentTypeQuantityPerFolder getDocumentTypeQuantityPerFolder() {
        return DocumentTypeQuantityPerFolder.ONE;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }

    @Override
    public String getContent() {
        return getDocumentType() + getFullName().toString() + getCpf() + getEmail();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    static class PersonalInfoAttributes {

        private String fullName;
        private String cpf;
        private String email;
    }
}
