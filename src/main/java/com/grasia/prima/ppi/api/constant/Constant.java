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
    public static final String DIVISION_ID_REQUIRED = "Division Id Is Required";
    public static final String DIVISION_NAME_REQUIRED = "Division Name Is Required";
    public static final String PERIOD_ID_REQUIRED = "Period Id Is Required";
    public static final String PERIOD_NAME_REQUIRED = "Period Name Is Required";
    public static final String START_DATE_REQUIRED = "Start Date Is Required";
    public static final String END_DATE_REQUIRED = "End Date Is Required";
    public static final String STATUS_REQUIRED = "Status Is Required";
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
    public static final String SUB_MENU_NAME_REQUIRED = "Sub Menu Name Is Required";
    public static final String SUB_MENU_ROUTE_REQUIRED = "Sub Menu Route Is Required";
    public static final String SUB_MENU_SEQUENCE_REQUIRED = "Sub Menu Sequence Is Required";
    public static final String MENU_ID_REQUIRED = "Menu Id Is Required";
    public static final String SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED = "Sub Menu Sequence Has Been Registered";
    public static final String START_END_DATE_NOT_VALID = "Start Date Must Less Than End Date";
    public static final String FILE_NOT_ALLOWED = "File Not Allowed";
    public static final String MAX_FILE_SIZE_IS_10_MB = "Maximum File Size Is 10 MB";
    public static final String FILE_DOES_NOT_EXIST = "File Doesn't Exist";
    public static final String STAFF_NAME_REQUIRED = "Staff Name Is Required";
    public static final String STAFF_POSITION_REQUIRED = "Staff Position Is Required";
    public static final String STAFF_IS_HEAD_REQUIRED = "Staff Is Head Is Required";
    public static final String PHOTO_FILE_NAME_REQUIRED = "Photo File Name Is Required";
    public static final String REGEX_DOT = "\\.";
    public static final String HEADER_INPUT_STREAM = "attachment; filename=";
    public static final String NEWSLETTER_TITLE_REQUIRED = "Newsletter Title Is Required";
    public static final String NEWSLETTER_DESCRIPTION_REQUIRED = "Newsletter Description Is Required";
    public static final String NEWSLETTER_COVER_REQUIRED = "Newsletter Cover Is Required";
    public static final String NEWSLETTER_FILE_REQUIRED = "Newsletter File Is Required";
    public static final String SLUG_FROM_TITLE_ALREADY_EXIST = "Slug From Title Already Exist, Please Change The Title";
    public static final String EVENT_ID_REQUIRED = "Event Id Is Required";
    public static final String EVENT_TITLE_REQUIRED = "Event Title Is Required";
    public static final String EVENT_DESCRIPTION_REQUIRED = "Event Description Is Required";
    public static final String EVENT_COVER_REQUIRED = "Event Cover Is Required";
    public static final String EVENT_START_DATE_REQUIRED = "Event Start Date Is Required";
    public static final String EVENT_END_DATE_REQUIRED = "Event End Date Is Required";
    public static final String GALLERY_FILE_REQUIRED = "Gallery File Is Required";
}
