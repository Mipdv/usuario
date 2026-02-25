package com.henrique.aprendendospring.infrascruture.exceptions;

public class ConflictExcpection extends RuntimeException{
    public ConflictExcpection(String mensagem){
        super(mensagem);
    }

    public ConflictExcpection(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
