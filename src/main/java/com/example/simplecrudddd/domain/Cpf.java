package com.example.simplecrudddd.domain;

import com.example.simplecrudddd.common.Result;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Cpf {

    private String value;

    public Cpf(String value) {
        this();

        new CPFValidator().assertValid(value);

        this.value = value;
    }

    public static Result<Cpf> create(String value) {
        try {
            return Result.ok(new Cpf(value));
        } catch (InvalidStateException ex) {
            return Result.error("Invalid CPF");
        }
    }
}
