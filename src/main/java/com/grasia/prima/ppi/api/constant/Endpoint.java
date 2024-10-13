package com.grasia.prima.ppi.api.constant;

import com.grasia.prima.ppi.api.exception.BusinessException;

public final class Endpoint {

    private Endpoint() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static final String BASE = "/api";
    public static final String AUTH = BASE + "/auths";
    public static final String USER_ROLE = BASE + "/user-roles";
    public static final String USER_ROLE_MENU = USER_ROLE + "/menus";
    public static final String USER = BASE + "/users";
    public static final String SYSTEM_PARAMETER = BASE + "/system-parameters";
    public static final String SYSTEM_PARAMETER_LIST = BASE + "/system-parameter-lists";
    public static final String DIVISION = BASE + "/divisions";
    public static final String PERIOD = BASE + "/periods";
    public static final String MENU = BASE + "/menus";
    public static final String SUB_MENU = BASE + "/sub-menus";
    public static final String STAFF = BASE + "/staffs";
    public static final String FILE = BASE + "/files";
    public static final String NEWSLETTER = BASE + "/newsletters";
}
