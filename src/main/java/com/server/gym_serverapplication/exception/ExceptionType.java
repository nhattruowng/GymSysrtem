package com.server.gym_serverapplication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ExceptionType {
    NULL_POINTER_EXCEPTION("NullPointerException"),
    ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException"),
    IO_EXCEPTION("IOException"),
    SQL_EXCEPTION("SQLException"),
    AUTHENTICATION_EXCEPTION("AuthenticationException"),
    ACCESS_DENIED_EXCEPTION("AccessDeniedException"),
    FORBIDDEN_EXCEPTION("ForbiddenException"),
    AUTHENTICATION_FAILED("AuthenticationFailureException"),
    ACCOUNT_NOT_FOUND_EXCEPTION("AccountNotFoundException"),
    UNKNOWN_EXCEPTION("UnknownException"),
    DUPLICATE_EXCEPTION("DuplicateException"),
    INVALID_PASSWORD("Invalid_PasswordException"),

    TYPE_NOT_FOUND_EXCEPTION("TypeNotFoundException"),
    SUBJECT_NOT_FOUND_EXCEPTION("SubjectNotFoundException"),
    USER_NOT_FOUND_EXCEPTION("UserNotFoundException"),
    COMMENT_NOT_FOUND_EXCEPTION("CommentNotFoundException"),
    SUBJECT_ALREADY_FOLLOWED_EXCEPTION("SubjectAlreadyFollowedException"),
    TYPE_DUPLICATE_EXCEPTION("TypeDuplicateException"),
    TYPE_NAME_NOT_FOUND_EXCEPTION("TypeNameNotFoundException"),

    FILE_NOT_VALUE("FileNotFoundException"),

    ARTICLE_NOT_FOUND_EXCEPTION("articleNotFound");


    private final String exceptionName;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionType.class);

    ExceptionType(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public void log(Exception e) {
        logger.error("Exception Occurred: {}, Message: {}", exceptionName, e.getMessage(), e);
    }
}
