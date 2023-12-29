package com.claro.amx.sp.dataconsumptionservice.commons.resolver;

import com.claro.amx.sp.dataconsumptionservice.model.response.ServiceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.validation.ConstraintViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomExceptionResolverDelegateTest {

    @Test
    void buildServiceResponse() {
       Exception ex = new Exception();
       ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(ex);
       assertEquals("900000", serviceResponse.getResultCode());
       assertTrue(serviceResponse.getResultMessage().contains("Error General ->"));
    }

    @Test
    void buildServiceResponse2() {
        ConstraintViolationException cv = new ConstraintViolationException("No se puede procesar la informacion entrante", Set.of());
        ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(cv);
        assertEquals("100001", serviceResponse.getResultCode());
    }

    @Test
    void buildServiceResponse3() {
        HttpMessageNotReadableException httpe = new HttpMessageNotReadableException("Error el campo ratingGroupList debería ser un array");
        ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(httpe);
        assertEquals("100004", serviceResponse.getResultCode());
        assertEquals(httpe.getMessage(), "Error el campo ratingGroupList debería ser un array");
    }

    @Test
    void buildServiceResponse4() {
        RedisConnectionFailureException rfe = new RedisConnectionFailureException("");
        ServiceResponse serviceResponse = CustomExceptionResolverDelegate.buildServiceResponse(rfe);
        assertEquals("200101", serviceResponse.getResultCode());
    }
}