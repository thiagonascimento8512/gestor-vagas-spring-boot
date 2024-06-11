package com.thiagonascimento.gestordevagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super("User already exists");
    }
}
