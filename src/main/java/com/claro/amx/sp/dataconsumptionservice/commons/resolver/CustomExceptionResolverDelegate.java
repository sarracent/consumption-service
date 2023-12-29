package com.claro.amx.sp.dataconsumptionservice.commons.resolver;


import com.claro.amx.sp.dataconsumptionservice.exception.CustomException;
import com.claro.amx.sp.dataconsumptionservice.model.response.ServiceResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.*;

public class CustomExceptionResolverDelegate {

    private CustomExceptionResolverDelegate() {
    }

    public static ServiceResponse buildServiceResponse(Exception ex) {
        if (ex instanceof CustomException) {
            CustomException customException = (CustomException) ex;
            return ServiceResponse.builder()
                    .resultCode(customException.getCode())
                    .resultMessage(customException.getMessage())
                    .build();
      
        } else if (ex instanceof MethodArgumentNotValidException ) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;

            List<String> errors = methodArgumentNotValidException.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ServiceResponse.builder()
                    .resultCode(ERROR_BADREQUEST_FAIL_ROAMING.getCode())
                    .resultMessage(errors.toString())
                    .build();

        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;

            List<String> errors = new ArrayList<>();

            constraintViolationException.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

            return ServiceResponse.builder()
                    .resultCode(ERROR_BADREQUEST_GENERAL.getCode())
                    .resultMessage(errors.toString())
                    .build();

        } else if (ex instanceof MissingRequestHeaderException) {
            MissingRequestHeaderException missingRequestHeaderException = (MissingRequestHeaderException) ex;

            return ServiceResponse.builder()
                    .resultCode(ERROR_BADREQUEST_GENERAL.getCode())
                    .resultMessage(missingRequestHeaderException.getMessage())
                    .build();

        } else if (ex instanceof HttpMessageNotReadableException) {

            return ServiceResponse.builder()
                    .resultCode(ERROR_BADREQUEST_FAIL_RATINGGROUPLIST.getCode())
                    .resultMessage(ERROR_BADREQUEST_FAIL_RATINGGROUPLIST.getMessage())
                    .build();

        } else if (ex instanceof RedisConnectionFailureException) {

            return ServiceResponse.builder()
                    .resultCode(ERROR_REDIS_SERVICE_DOWN.getCode())
                    .resultMessage(ERROR_REDIS_SERVICE_DOWN.getMessage())
                    .build();

        } else {
            return ServiceResponse.builder()
                    .resultCode(ERROR_GENERAL.getCode())
                    .resultMessage(String.format(ERROR_GENERAL.getMessage(), ex.getMessage(), ex))
                    .build();
        }
    }

}
