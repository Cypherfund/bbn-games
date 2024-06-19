package com.cypherfund.bbn.exception;


import com.cypherfund.bbn.models.ApiResponse;

public class CustomFeignClientException extends RuntimeException{
    ApiResponse<?> apiResponse;
    public CustomFeignClientException(ApiResponse<?> apiResponse,  String message) {
        super(message);
        this.apiResponse = apiResponse;
    }
}
