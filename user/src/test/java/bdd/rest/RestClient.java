// Copyright (c) 2019 Travelex Ltd

package bdd.rest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestClient {

    public List<ResponseEntity> responseObjects = new ArrayList<>();

    public Object lastResponse;

    public Object lastRequest;

    public int lastStatusCode;

    public HttpClientErrorException lastException;

    RestTemplate restTemplate;

    public RestClient() {
        HttpClient client = HttpClientBuilder.create().build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(client);

        restTemplate = new RestTemplate(requestFactory);
    }

    public ResponseEntity sendPostRequestToUrl(Object objectToSend, String url, Class responseClass) {
        lastRequest = objectToSend;
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, objectToSend, responseClass);
        } catch (HttpClientErrorException error) {
            lastResponse = error;
            lastStatusCode = error.getRawStatusCode();
        }
        this.lastResponse = responseEntity;
        responseObjects.add(responseEntity);
        return responseEntity;
    }

    public ResponseEntity sendGetRequestToUrl(String url, Class responseObject) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, responseObject);
            lastStatusCode = responseEntity.getStatusCodeValue();
        } catch (HttpClientErrorException error) {
            lastResponse = error;
            lastStatusCode = error.getRawStatusCode();
        }
        this.lastResponse = responseEntity;
        responseObjects.add(responseEntity);
        return responseEntity;
    }
}
