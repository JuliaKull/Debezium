package com.knits.enterprise.exceptions;

public class ExceptionCodes {
    public static final int UNMAPPED_EXCEPTION_CODE=-1024;
    public static final String INTERNAL_EX_MESSAGE="Internal System Exception. Contact administrator if problem persists";

    public static final int EMPLOYEE_ALREADY_EXISTS=-101;
    public static final int EMPLOYEE_NOT_FOUND = -102;

    public static final int NOT_AUTHENTICATED = -800;
    public static final int AUTH_MEMBER_NOT_FOUND = -820;
    public static final int AUTH_MEMBER_NOT_ENABLED =-810 ;
    public static final int JWT_TOKEN_EXPIRED = -830 ;
}
