package com.example.demoOne.error_handling;

import ch.qos.logback.core.net.SocketConnector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.List;


public class ResponseEntityErrorHandler implements ResponseErrorHandler {

    private List<HttpMessageConverter<?>> messageConverters;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return hasError(response.getStatusCode());
    }

    protected boolean hasError(HttpStatus statusCode) {
        return (statusCode.is4xxClientError() || statusCode.is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpMessageConverterExtractor<ErrorResponse> errorMessageExtractor =
                new HttpMessageConverterExtractor(ErrorResponse.class, messageConverters);
        ErrorResponse errorObject = errorMessageExtractor.extractData(response);
        throw new ResponseEntityErrorException(ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).body(errorObject));
    }

    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        this.messageConverters = messageConverters;
    }
}