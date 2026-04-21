package com.javanauta.usuario.infrastructure.exceptions;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String menssage) {
        super(menssage);
    }

    public IllegalArgumentException(String menssage, Throwable throwable){
        super(menssage, throwable);
    }

}
