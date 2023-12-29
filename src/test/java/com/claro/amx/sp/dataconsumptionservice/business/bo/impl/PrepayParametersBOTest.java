package com.claro.amx.sp.dataconsumptionservice.business.bo.impl;

import com.claro.amx.sp.dataconsumptionservice.business.bo.PrepayParametersBO;
import com.claro.amx.sp.dataconsumptionservice.dao.ccard.PrepayParametersDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PrepayParametersBOTest {
    @Mock
    private PrepayParametersDAO parametersDAO;
    private PrepayParametersBO parametersBO;

    @BeforeEach
    void setUp() {
        parametersBO = new PrepayParametersBOImpl(parametersDAO);
    }

    @Test
    void testGetPrepayParameters() {
        when(parametersDAO.getPrepayParametersData(anyString())).thenReturn(List.of(PrepayParameters.builder()
                .name("test")
                .value("test")
                .build()));
        final PrepayParameters result = parametersBO.getPrepayParameters("test");
        assertNotNull(result);
        assertEquals("test", result.getName());
        assertEquals("test", result.getValue());
    }

    @Test
    void testGetPrepayParametersIsEmptyList() {
        when(parametersDAO.getPrepayParametersData(anyString())).thenReturn(Collections.emptyList());

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            parametersBO.getPrepayParameters("test");
        });

        assertEquals("200002", thrown.getCode());
        assertEquals("Error no se encontraron registros en PREPAY_PARAMETERS con el valor test",
                thrown.getMessage());
    }

    @Test
    void testGetPrepayParametersIsDuplicate() {
        when(parametersDAO.getPrepayParametersData(anyString())).thenReturn(List.of(PrepayParameters.builder()
                .name("test")
                .value("test")
                .build(),
                PrepayParameters.builder()
                .name("test")
                .value("test")
                .build()));

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            parametersBO.getPrepayParameters("test");
        });

        assertEquals("200001", thrown.getCode());
        assertEquals("Error se encontraron 2 o mas registros en PREPAY_PARAMETERS con el valor test",
                thrown.getMessage());
    }

    @Test
    void testGetPrepayParametersIsNullOrEmptyValue() {
        when(parametersDAO.getPrepayParametersData(anyString())).thenReturn(List.of(PrepayParameters.builder()
                .build()));

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            parametersBO.getPrepayParameters("test");
        });

        assertEquals("200008", thrown.getCode());
        assertEquals("Error no se encontraron datos cargados en el registro de PREPAY_PARAMETERS con el valor test",
                thrown.getMessage());
    }
}