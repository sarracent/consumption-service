package com.claro.amx.sp.dataconsumptionservice.exception.impl;

import com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType;
import com.claro.amx.sp.dataconsumptionservice.exception.CustomException;

import java.util.Collections;
import java.util.List;

public class BusinessException extends RuntimeException implements CustomException {

    private static final long serialVersionUID = -1132348466005485433L;
    private final String code;
    private final String message;
    private final transient List<Object> extraInfo;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.extraInfo = Collections.emptyList();
    }

    public BusinessException(String code, String message, List<Object> extraInfo) {
        super(message);
        this.code = code;
        this.message = message;
        this.extraInfo = extraInfo;
    }

    @Override
    public ExceptionType getExceptionType() {
        return ExceptionType.BUSINESS_EXCEPTION;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<Object> getExtraInfo() {
        return extraInfo;
    }

}
