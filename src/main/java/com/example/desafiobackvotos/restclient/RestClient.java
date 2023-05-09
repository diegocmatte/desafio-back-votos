package com.example.desafiobackvotos.restclient;

import com.example.desafiobackvotos.exception.NoDocumentFoundException;
import com.example.desafiobackvotos.restclient.response.UserResponse;
import com.example.desafiobackvotos.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

@Component
public class RestClient {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${user.info.url}")
    private String url;

    public Boolean validateDocument(String document){
        try {
            URI uri = URI.create(String.format((url), document));
            HttpEntity<UserResponse> request = new HttpEntity<>(new UserResponse("bar"));
            return restTemplate.exchange(uri, HttpMethod.GET, request, UserResponse.class).hasBody();
        } catch (HttpServerErrorException httpClientErrorException){
            throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (NoDocumentFoundException noDocumentFoundException) {
            throw new NoDocumentFoundException(Constants.DOCUMENT_NOT_FOUND);
        }
    }
}
