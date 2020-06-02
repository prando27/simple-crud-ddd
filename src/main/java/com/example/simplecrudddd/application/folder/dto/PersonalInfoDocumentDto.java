package com.example.simplecrudddd.application.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonalInfoDocumentDto {

    private final String fullName;

    private final String cpf;

    private final String email;
}
