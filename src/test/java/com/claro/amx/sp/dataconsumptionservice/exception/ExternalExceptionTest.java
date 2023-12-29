package com.claro.amx.sp.dataconsumptionservice.exception;

import com.claro.amx.sp.dataconsumptionservice.exception.impl.ExternalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.EXTERNAL_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExternalExceptionTest {
    private ExternalException externalException;

    @BeforeEach
    void setUp() {
        externalException = new ExternalException("0000", "External Exception Test");
    }

    @Test
    void getDescription() {
        String message = "External Exception Test";
        String code = "0000";
        try {
            throw new ExternalException(code, message, null);
        } catch (ExternalException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getDescriptionTestSucces() {
        String message = "External Exception Test";
        String code = "0000";
        try {
            throw new ExternalException(code, message);
        } catch (ExternalException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getExceptionType() {
        final ExceptionType exceptionType = externalException.getExceptionType();
        assertEquals(EXTERNAL_EXCEPTION, exceptionType);
    }

    @Test
    void getCode() {
        final String code = externalException.getCode();
        assertEquals("0000", code);
    }

    @Test
    void getMessage() {
        final String message = externalException.getMessage();
        assertEquals("External Exception Test", message);
    }

    @Test
    void getExtraInfo() {
        final List<Object> list = externalException.getExtraInfo();
        assertNotNull(list);
    }
}