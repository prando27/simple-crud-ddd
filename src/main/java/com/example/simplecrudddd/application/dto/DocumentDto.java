package com.example.simplecrudddd.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties({"attributes", "documentTypeQuantityPerFolder"})
public class DocumentDto {

    @JsonUnwrapped
    private final Object document;
}
