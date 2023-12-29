package com.claro.amx.sp.dataconsumptionservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.CUSTOM_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomExceptionTest {
    private CustomException customException;

    @BeforeEach
    void setUp() {
        customException = new CustomException() {
            @Override
            public String getCode() {
                return "0000";
            }

            @Override
            public String getMessage() {
                return "Custom Exception Test";
            }
        };
    }

    @Test
    void getExceptionType() {
        final ExceptionType exceptionType = customException.getExceptionType();
        assertEquals(CUSTOM_EXCEPTION, exceptionType);
    }

    @Test
    void getExtraInfo() {
        final List<Object> list = customException.getExtraInfo();
        assertNotNull(list);
    }
}