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
    public static final String USERNAME_HAS_BEEN_REGISTERED = "Username Has Been Registered";
    public static final String EMAIL_HAS_BEEN_REGISTERED = "Email Has Been Registered";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String ERROR = "Error : {}";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USERNAME_REQUIRED = "Username Is Required";
    public static final String PASSWORD_REQUIRED = "Password Is Required";
    public static final String PASSWORD_CONFIRM_REQUIRED = "Password Confirm Is Required";
    public static final String FULL_NAME_REQUIRED = "Full Name Is Required";
    public static final String EMAIL_REQUIRED = "Email Is Required";
    public static final String BIRTH_DATE_REQUIRED = "Birth Date Is Required";
    public static final String EDUCATION_REQUIRED = "Education Is Required";
    public static final String GRADUATION_REQUIRED = "Graduation Is Required";
    public static final String USER_ROLE_ID_REQUIRED = "User Role Id Is Required";
    public static final String GENDER_ID_REQUIRED = "Gender Id Is Required";
    public static final String ROLE_NAME_REQUIRED = "Role Name Is Required";
    public static final String DEPARTMENT_NAME_REQUIRED = "Department Name Is Required";
    public static final String COMMITTEE_NAME_REQUIRED = "Committee Name Is Required";
    public static final String START_DATE_REQUIRED = "Start Date Is Required";
    public static final String END_DATE_REQUIRED = "End Date Is Required";
    public static final String SYSTEM_PARAMETER_NAME_REQUIRED = "Parameter Name Is Required";
    public static final String SYSTEM_PARAMETER_ID_REQUIRED = "Parameter Id Is Required";
    public static final String SYSTEM_PARAMETER_LIST_NAME_REQUIRED = "Parameter List Name Is Required";
    public static final String REFRESH_TOKEN_REQUIRED = "Refresh Token Is Required";
    public static final String HEADER = "header";
    public static final String MIN_PASSWORD = String.format("Minimum Password Length Is %s Character", Constant.MIN_PASSWORD_LENGTH);
    public static final int MIN_PASSWORD_LENGTH = 10;
    public static final String MUST_CONTAIN_UPPER_CASE = "Password Must Consist of Capital Letter";
    public static final String MUST_CONTAIN_NUMBER = "Password Must Consist of Number";
    public static final String PASSWORD_CONFIRM_NOT_EQUALS = "Password Confirm Is Not The Same As Password";
    public static final String SEQUENCE_MUST_GREATER_THAN_ZERO = "Sequence Must Greater Than Zero";
    public static final String MENU_SEQUENCE_HAS_BEEN_REGISTERED = "Menu Sequence Has Been Registered";
    public static final String MENU_NAME_REQUIRED = "Menu Name Is Required";
    public static final String MENU_ROUTE_REQUIRED = "Menu Route Is Required";
    public static final String MENU_ICON_REQUIRED = "Menu Icon Is Required";
    public static final String MENU_SEQUENCE_REQUIRED = "Menu Sequence Is Required";
}
