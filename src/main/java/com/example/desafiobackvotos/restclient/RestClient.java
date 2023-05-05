package com.example.desafiobackvotos.restclient;

import com.example.desafiobackvotos.exception.NoDocumentFoundException;
import com.example.desafiobackvotos.restclient.response.UserResponse;
import com.example.desafiobackvotos.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@Component
public class RestClient {

    private RestTemplate restTemplate;

    @Value("user.info.url")
    private String url;

    public Boolean validateDocument(String document){
        try {
            URI uri = URI.create(String.format((url), document));
            return Objects.requireNonNull(restTemplate.getForObject(uri, UserResponse.class)).isAbleToVote();
        } catch (HttpClientErrorException httpClientErrorException){
            throw new NoDocumentFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }
}
