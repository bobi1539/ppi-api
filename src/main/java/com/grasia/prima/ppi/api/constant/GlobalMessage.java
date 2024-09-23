package com.grasia.prima.ppi.api.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalMessage {
    SUCCESS(HttpStatus.OK, Constant.SUCCESS),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, Constant.UNAUTHORIZED),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, Constant.DATA_NOT_FOUND),
    WRONG_USERNAME_OR_PASSWORD(HttpStatus.BAD_REQUEST, Constant.WRONG_USERNAME_OR_PASSWORD),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, Constant.INTERNAL_SERVER_ERROR);

    public final HttpStatus status;
    public final String message;
}
