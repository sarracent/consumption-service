package com.claro.amx.sp.dataconsumptionservice.manager;

import com.claro.amx.sp.dataconsumptionservice.model.cache.CachePrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.repository.PrepayParametersRepository;
import com.claro.amx.sp.dataconsumptionservice.service.PrepayParametersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_SOCIAL_NETWORKS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrepayParametersManagerTest {
    @Mock
    private PrepayParametersService prepayParametersService;
    @Mock
    private PrepayParametersRepository parametersRepository;
    private PrepayParametersManager prepayParametersManager;

    @BeforeEach
    void setUp() {
        prepayParametersManager = new PrepayParametersManager(prepayParametersService, parametersRepository);
    }

    @Test
    void testGetPrepayParametersWithoutRedisCache() {
        when(prepayParametersService.getPrepayParameters(anyString())).thenReturn(getPrepayParameters());

        final PrepayParameters result = prepayParametersManager.getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS);

        assertNotNull(result);
        assertEquals(RATING_GROUPS_SOCIAL_NETWORKS, result.getName());
        assertEquals("#992#993#994#995#997#", result.getValue());
    }

    @Test
    void testGetPrepayParametersWithRedisCache() {
        when(parametersRepository.findById(anyString())).thenReturn(Optional.of(CachePrepayParameters.builder()
                .key("RATING_GROUPS_SOCIAL_NETWORKS_AR")
                .prepayParameters(getPrepayParameters()).build()));

        final PrepayParameters result = prepayParametersManager.getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS);

        assertNotNull(result);
        assertEquals(RATING_GROUPS_SOCIAL_NETWORKS, result.getName());
        assertEquals("#992#993#994#995#997#", result.getValue());
    }

    private PrepayParameters getPrepayParameters() {
        return PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#993#994#995#997#")
                .build();
    }

    @Test
    void testRemoveAll() {
       doNothing().when(parametersRepository).deleteAll();
       prepayParametersManager.removeAll();
       verify(parametersRepository,times(1)).deleteAll();
    }
}