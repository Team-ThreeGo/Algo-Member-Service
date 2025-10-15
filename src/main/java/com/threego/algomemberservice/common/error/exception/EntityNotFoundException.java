package com.threego.algomemberservice.common.error.exception;

import com.threego.algomemberservice.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public EntityNotFoundException(final ErrorCode errorCode, final String message) {
        super(errorCode, message);
    }
}