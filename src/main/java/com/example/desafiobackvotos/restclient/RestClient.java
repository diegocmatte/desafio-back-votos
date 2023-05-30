package com.example.desafiobackvotos.restclient;

import com.example.desafiobackvotos.exception.NoDocumentFoundException;
import com.example.desafiobackvotos.restclient.response.UserResponse;
import com.example.desafiobackvotos.util.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class RestClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${user.info.url}")
    private String url;

    public Boolean validateDocument(String document){
        try {
            URI uri = URI.create(String.format((url), document));
            UserResponse userInfoResponse = restTemplate.getForObject(uri, UserResponse.class);
            return userInfoResponse.isAbleToVote();
        } catch (HttpServerErrorException httpClientErrorException){
            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (NoDocumentFoundException noDocumentFoundException) {
            throw new NoDocumentFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }
}
