package com.grasia.prima.ppi.api.constant;

import com.grasia.prima.ppi.api.exception.BusinessException;

public final class Endpoint {

    private Endpoint() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static final String BASE = "/api";
    public static final String AUTH = BASE + "/auths";
    public static final String POSITION = BASE + "/positions";
    public static final String EMPLOYEE = BASE + "/employees";
}
