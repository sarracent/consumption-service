package com.claro.amx.sp.dataconsumptionservice.commons.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogAspectTest {
    LogAspect logAspect;
    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @BeforeEach
    public void setUp() {
        logAspect = new LogAspect();
    }

    @Test
    void logExecutionTime() {
        Exception exception = assertThrows(Exception.class, () -> {
            logAspect.logExecutionTime(null);
        });
        assertNull(exception.getMessage());
    }


    @Test
    void logOperationTime() {
        Exception exception = assertThrows(Exception.class, () -> {
            logAspect.logOperationTime(null);
        });
        assertNull(exception.getMessage());
    }

    @Test()
    void afterAnyException() {
        Exception exception = new Exception();
        logAspect.afterAnyException(null, exception);
        assertNotNull(exception);
    }

}