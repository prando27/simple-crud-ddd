package com.example.simplecrudddd.application.folder.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderDto {

    private final Long id;

    private final Long userId;

    private List<DocumentDto> documents;
}
