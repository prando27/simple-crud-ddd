package com.example.simplecrudddd.application.folder.dto.update;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdatePersonalInfoDocumentDto extends UpdateDocumentDto {

    private String fullName;

    private String cpf;

    private String email;
}
