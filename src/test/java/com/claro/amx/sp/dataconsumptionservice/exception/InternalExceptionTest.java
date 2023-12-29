package com.claro.amx.sp.dataconsumptionservice.exception;

import com.claro.amx.sp.dataconsumptionservice.exception.impl.InternalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.INTERNAL_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class InternalExceptionTest {
    private InternalException internalException;

    @BeforeEach
    void setUp() {
        internalException = new InternalException("0000", "Internal Exception Test");
    }

    @Test
    void getDescription() {
        String description = "Internal Exception Test";
        String code = "0000";
        try {
            throw new InternalException(code, description, null);
        } catch (InternalException e) {
            assertEquals(description, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getDescriptionTestSucces() {
        String message = "Internal Exception Test";
        String code = "0000";
        try {
            throw new InternalException(code, message);
        } catch (InternalException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getExceptionType() {
        final ExceptionType exceptionType = internalException.getExceptionType();
        assertEquals(INTERNAL_EXCEPTION, exceptionType);
    }

    @Test
    void getCode() {
        final String code = internalException.getCode();
        assertEquals("0000", code);
    }

    @Test
    void getMessage() {
        final String message = internalException.getMessage();
        assertEquals("Internal Exception Test", message);
    }

    @Test
    void getExtraInfo() {
        final List<Object> list = internalException.getExtraInfo();
        assertNotNull(list);
    }

}