package com.example.simplecrudddd.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreatePersonalInfoDocumentDto extends CreateDocumentDto {

    private String fullName;

    private String cpf;

    private String email;
}
