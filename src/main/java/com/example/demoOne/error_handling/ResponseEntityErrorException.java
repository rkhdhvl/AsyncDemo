package com.example.demoOne.error_handling;

import org.springframework.http.ResponseEntity;


public class ResponseEntityErrorException extends RuntimeException {
    private ResponseEntity<ErrorResponse> errorResponse;
    public ResponseEntityErrorException(ResponseEntity<ErrorResponse> errorResponse) {
        this.errorResponse = errorResponse;
    }
    public ResponseEntity<ErrorResponse> getErrorResponse() {
        return errorResponse;
    }
}
