package com.example.simplecrudddd.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRgDocumentDto extends CreateDocumentDto {

    private String fileName;
}
