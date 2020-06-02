package com.example.simplecrudddd.domain;

import com.example.simplecrudddd.common.Result;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Name {

    public static final Name NONE = new Name();

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
            return Result.error(ex.getMessage());
        }
    }
}
