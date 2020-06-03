package com.example.simplecrudddd.domain.folder.entity.document;

import static java.util.Optional.ofNullable;

import java.util.Optional;

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

    public Optional<Name> getFullName() {
        return ofNullable(attributes.getFullName())
                .map(Name::new);
    }

    public Optional<Cpf> getCpf() {
        return ofNullable(attributes.getCpf())
                .map(Cpf::new);
    }

    public Optional<Email> getEmail() {
        return ofNullable(attributes.getEmail())
                .map(Email::new);
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

    public void changeFullName(Name fullName) {
        this.attributes.fullName = fullName.getValue();
    }

    public void changeCpf(Cpf cpf) {
        this.attributes.cpf = cpf.getValue();
    }

    public void changeEmail(Email email) {
        this.attributes.email = email.getValue();
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
