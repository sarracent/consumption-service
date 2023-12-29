package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.business.bo.PrepayParametersBO;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.service.PrepayParametersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_SOCIAL_NETWORKS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrepayParametersServiceTest {
    @Mock
    private PrepayParametersBO prepayParametersBO;
    private PrepayParametersService prepayParametersService;

    @BeforeEach
    void setUp() {
        when(prepayParametersBO.getPrepayParameters(anyString())).thenReturn(PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#993#994#995#997#")
                .build());

        prepayParametersService = new PrepayParametersServiceImpl(prepayParametersBO);
    }

    @Test
    void testGetPrepayParameters() {
        final PrepayParameters result = prepayParametersService.getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS);
        assertNotNull(result);
        assertEquals(RATING_GROUPS_SOCIAL_NETWORKS, result.getName());
        assertEquals("#992#993#994#995#997#", result.getValue());
    }
}