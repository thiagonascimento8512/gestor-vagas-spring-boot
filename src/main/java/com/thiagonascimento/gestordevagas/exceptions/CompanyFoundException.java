package com.thiagonascimento.gestordevagas.exceptions;

public class CompanyFoundException extends RuntimeException {
    public CompanyFoundException() {
        super("Username or email already in use");
    }
}
