package com.example.simplecrudddd.domain.folder.entity.document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DocumentType {

    PERSONAL_INFO(Constants.PERSONAL_INFO),
    ADDRESS(Constants.ADDRESS),
    CNH(Constants.CNH),
    RG(Constants.RG);

    public final String name;

    public static class Constants {

        public static final String PERSONAL_INFO = "PERSONAL_INFO";
        public static final String ADDRESS = "ADDRESS";
        public static final String CNH = "CNH";
        public static final String RG = "RG";
    }
}
