package com.example.simplecrudddd.application.folder.dto.create;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreatePersonalInfoDocumentDto extends CreateDocumentDto {

    private String fullName;

    private String cpf;

    private String email;
}
