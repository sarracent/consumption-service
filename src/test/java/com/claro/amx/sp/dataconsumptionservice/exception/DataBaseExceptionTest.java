package com.claro.amx.sp.dataconsumptionservice.exception;

import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.exception.ExceptionType.DATABASE_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataBaseExceptionTest {
    private DataBaseException dataBaseException;

    @BeforeEach
    void setUp() {
        dataBaseException = new DataBaseException("0000", "Data Base Exception Test");
    }

    @Test
    void getDescriptionTest_Succes() {
        String message = "Data Base Exception Test";
        String code = "0000";
        try {
            throw new DataBaseException(code, message, null);
        } catch (DataBaseException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getDescriptionTestSucces() {
        String message = "Data Base Exception Test";
        String code = "0000";
        try {
            throw new DataBaseException(code, message);
        } catch (DataBaseException e) {
            assertEquals(message, e.getMessage());
            assertEquals(code, e.getCode());
        }
    }

    @Test
    void getExceptionType() {
        final ExceptionType exceptionType = dataBaseException.getExceptionType();
        assertEquals(DATABASE_EXCEPTION, exceptionType);
    }

    @Test
    void getCode() {
        final String code = dataBaseException.getCode();
        assertEquals("0000", code);
    }

    @Test
    void getMessage() {
        final String message = dataBaseException.getMessage();
        assertEquals("Data Base Exception Test", message);
    }

    @Test
    void getExtraInfo() {
        final List<Object> list = dataBaseException.getExtraInfo();
        assertNotNull(list);
    }
}