package com.claro.amx.sp.dataconsumptionservice.commons.handler;

import com.claro.amx.sp.dataconsumptionservice.commons.aop.LogAspect;
import com.claro.amx.sp.dataconsumptionservice.commons.resolver.CustomExceptionResolverDelegate;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.*;
import com.claro.amx.sp.dataconsumptionservice.model.response.ServiceResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InternalException.class, TechnicalException.class, ExternalException.class,
            Exception.class})
    public final ResponseEntity<ServiceResponse> handleOtherExceptions(Exception exception, WebRequest request) {
        ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(exception);
        LogAspect.logFinishOperationInError(serviceResponse);
        return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadRequestException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class,
    MissingRequestHeaderException.class, HttpMessageNotReadableException.class})
    public final ResponseEntity<ServiceResponse> handleBadRequestExceptionExceptions(Exception exception, WebRequest request) {
        ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(exception);
        LogAspect.logFinishOperationInError(serviceResponse);
        return new ResponseEntity<>(serviceResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataBaseException.class, BusinessException.class, RedisConnectionFailureException.class})
    public final ResponseEntity<ServiceResponse> handleBusinessExceptions(Exception exception, WebRequest request) {
        ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(exception);
        LogAspect.logFinishOperationInError(serviceResponse);
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

}