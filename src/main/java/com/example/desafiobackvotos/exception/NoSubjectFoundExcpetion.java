package com.example.desafiobackvotos.exception;

import org.springframework.web.client.RestClientException;

public class NoSubjectFoundExcpetion extends RestClientException {

    private static final long serialVersionUID = 1L;

    public NoSubjectFoundExcpetion(String message){
        super(message);
    }
}
