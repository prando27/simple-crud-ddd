package com.example.simplecrudddd.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateRgDocumentDto extends UpdateDocumentDto {

    private String fileName;
}
