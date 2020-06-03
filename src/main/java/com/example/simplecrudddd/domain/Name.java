package com.example.simplecrudddd.domain;

import com.example.simplecrudddd.common.Result;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO - Considerar o uso de Bean Validation para as validações, porém sem lançar exceções e sim um Result.error com a mensagem da violação
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = false)
public class Name {

    private String value;

    public Name(String value) {
        this();

        if (value == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (value.length() > 100) {
            throw new IllegalArgumentException("Name cannot have more than 100 characters");
        }

        this.value = value;
    }

    public static Result<Name> create(String value) {
        try {
            return Result.ok(new Name(value));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Result.error(ex.getMessage());
        }
    }
}
