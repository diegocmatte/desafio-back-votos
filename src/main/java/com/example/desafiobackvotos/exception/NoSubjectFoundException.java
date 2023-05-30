package com.example.desafiobackvotos.exception;

import org.springframework.web.client.RestClientException;

public class NoSubjectFoundException extends RestClientException {

    private static final long serialVersionUID = 1L;

    public NoSubjectFoundException(String message){
        super(message);
    }
}
