package com.example.simplecrudddd.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateCnhDocumentDto extends UpdateDocumentDto {

    private String fileName;
}
