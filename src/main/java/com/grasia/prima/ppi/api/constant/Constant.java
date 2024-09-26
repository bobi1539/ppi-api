package com.grasia.prima.ppi.api.constant;

import com.grasia.prima.ppi.api.exception.BusinessException;

public final class Constant {

    private Constant() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static final String SUCCESS = "Success";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String DATA_NOT_FOUND = "Data Not Found";
    public static final String WRONG_USERNAME_OR_PASSWORD = "Wrong Username Or Password";
    public static final String REFRESH_TOKEN_NOT_VALID = "Refresh Token Not Valid";
    public static final String CANNOT_DELETE_THIS_DATA = "Cannot Delete This Data";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String ERROR = "Error : {}";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USERNAME_REQUIRED = "Username Is Required";
    public static final String PASSWORD_REQUIRED = "Password Is Required";
    public static final String ROLE_NAME_REQUIRED = "Role Name Is Required";
    public static final String SYSTEM_PARAMETER_NAME_REQUIRED = "Parameter Name Is Required";
    public static final String SYSTEM_PARAMETER_ID_REQUIRED = "Parameter Id Is Required";
    public static final String SYSTEM_PARAMETER_LIST_NAME_REQUIRED = "Parameter List Name Is Required";
    public static final String REFRESH_TOKEN_REQUIRED = "Refresh Token Is Required";
    public static final String HEADER = "header";
}
