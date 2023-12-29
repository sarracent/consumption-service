package com.claro.amx.sp.dataconsumptionservice.exception;

import com.claro.amx.sp.dataconsumptionservice.exception.impl.TechnicalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.TECHNICAL_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TechnicalExceptionTest {
    private TechnicalException technicalException;

    @BeforeEach
    void setUp() {
        technicalException = new TechnicalException("0000", "Technical Exception Test");
    }

    @Test
    void getDescription() {
        String message = "Technical Exception Test";
        String code = "0000";
        try {
            throw new TechnicalException(code, message, null);
        } catch (TechnicalException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getDescriptionTestSucces() {
        String message = "Technical Exception Test";
        String code = "0000";
        try {
            throw new TechnicalException(code, message);
        } catch (TechnicalException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getExceptionType() {
        final ExceptionType exceptionType = technicalException.getExceptionType();
        assertEquals(TECHNICAL_EXCEPTION, exceptionType);
    }

    @Test
    void getCode() {
        final String code = technicalException.getCode();
        assertEquals("0000", code);
    }

    @Test
    void getMessage() {
        final String message = technicalException.getMessage();
        assertEquals("Technical Exception Test", message);
    }

    @Test
    void getExtraInfo() {
        final List<Object> list = technicalException.getExtraInfo();
        assertNotNull(list);
    }
}