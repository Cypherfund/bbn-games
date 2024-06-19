package com.cypherfund.bbn.exception;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author ngaielizabeth
 */
@Data
public class HttpErrorInfo {

    private LocalDateTime timestamp;
    private String path;
    private int errorCode;
    private String message;

    public HttpErrorInfo(int httpStatus, String path, String message) {
        timestamp = LocalDateTime.now();
        this.errorCode = httpStatus;
        this.path = path;
        this.message = message;
    }


}
