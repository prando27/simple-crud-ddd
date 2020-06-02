package com.example.simplecrudddd.domain.folder.document;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.DocumentType;
import com.example.simplecrudddd.domain.Email;
import com.example.simplecrudddd.domain.Name;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Example document
 *
 * Encapsulation = Data Integrity
 * Use of ValueObjects for attributes
 * Only Getters (Always get value from the backing value object)
 * Mutate methods that preserve encapsulation
 * Protected no-arg constructor for JPA/Hibernate purposes
 * Private all-args constructor to be used for static factory method
 * Static factory method for creation (JPA/Hibernate will create via reflection - no-arg constructor purpose)
 * DiscriminatorValue for document specialization (Always create/use DocumentType.Constants)
 * Private ValueObject attribute as a backing object for persistence attributes (See "attributes" attribute)
 * getContent method for content based search (if necessary)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@DiscriminatorValue(DocumentType.Constants.PERSONAL_INFO)
public class PersonalInfoDocument extends Document {

    //================================================================================
    // Accessors
    //================================================================================

    public Name getFullName() {
        return new Name(attributes.getFullName());
    }

    public Cpf getCpf() {
        return new Cpf(attributes.getCpf());
    }

    public Email getEmail() {
        return new Email(attributes.getEmail());
    }

    @Override
    public DocumentTypeLimitPerFolder getDocumentTypeLimitPerFolder() {
        return DocumentTypeLimitPerFolder.ONE;
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.PERSONAL_INFO;
    }

    @Override
    public String getContent() {
        return getDocumentType().toString()
                + getFullName()
                + getCpf()
                + getEmail();
    }

    //================================================================================
    // Mutator Methods
    //================================================================================

    /**
     * Use of value objects protect the data integrity
     */
    public void update(Name fullName, Cpf cpf, Email email) {
        this.attributes = new PersonalInfoAttributes(
                fullName.getValue(),
                cpf.getValue(),
                email.getValue());
    }

    //================================================================================
    // Static Factory Methods
    //================================================================================

    public static PersonalInfoDocument create(
            Name fullName,
            Cpf cpf,
            Email email) {

        return new PersonalInfoDocument(new PersonalInfoAttributes(
                fullName.getValue(),
                cpf.getValue(),
                email.getValue()));
    }

    //================================================================================
    // JPA/Hibernate persistence value objects and attributes
    //================================================================================

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Getter(AccessLevel.PRIVATE)
    private PersonalInfoAttributes attributes;

    /**
     * This class represents how data will be persisted in the JSONB column, it's a ValueObject
     */
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
