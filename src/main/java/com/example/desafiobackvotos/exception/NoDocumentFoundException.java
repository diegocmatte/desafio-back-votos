package com.example.desafiobackvotos.exception;

import org.springframework.web.client.RestClientException;

public class NoDocumentFoundException extends RestClientException {
    private static final long serialVersionUID = 1L;


    public NoDocumentFoundException(String msg) {
        super(msg);
    }
}
