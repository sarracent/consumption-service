package com.claro.amx.sp.dataconsumptionservice.manager;

import com.claro.amx.sp.dataconsumptionservice.business.bo.RatingGroupDescriptionBO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.cache.CacheRatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.repository.RatingGroupRepository;
import com.claro.amx.sp.dataconsumptionservice.service.RatingGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_SOCIAL_NETWORKS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingGroupDescriptionManagerTest {
    @Mock
    private RatingGroupService ratingGroupService;
    @Mock
    private RatingGroupDescriptionBO ratingGroupDescriptionBO;
    @Mock
    private RatingGroupRepository ratingGroupRepository;
    @Mock
    private PrepayParametersManager prepayParametersManager;
    private RatingGroupDescriptionManager ratingGroupDescriptionManager;

    @BeforeEach
    void setUp() {
        ratingGroupDescriptionManager = new RatingGroupDescriptionManager(ratingGroupService, ratingGroupDescriptionBO
                , ratingGroupRepository, prepayParametersManager);
    }
    @Test
    void testGetRatingGroupsDescriptionParameterNameWithoutRedisCache() {
        when(prepayParametersManager.getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS)).thenReturn(PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#")
                .build());
        when(ratingGroupService.getRatingGroupsDescription(List.of("992")))
                .thenReturn(List.of(RatingGroupDescription.builder()
                        .ratingGroupId("992")
                        .description("Facebook Gratis").build()));

        final List<RatingGroupDescription> result = ratingGroupDescriptionManager.getRatingGroupsDescription(RATING_GROUPS_SOCIAL_NETWORKS);

        assertNotNull(result);
        assertEquals("992", result.get(0).getRatingGroupId());
        assertEquals("Facebook Gratis", result.get(0).getDescription());
    }

    @Test
    void testGetRatingGroupsDescriptionParameterNameWithRedisCache() {
        when(prepayParametersManager.getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS)).thenReturn(PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#")
                .build());
        when(ratingGroupRepository.findById(anyString())).thenReturn(Optional.of(CacheRatingGroupDescription.builder()
                .key("RATING_GROUPS_SOCIAL_NETWORKS_AR")
                .ratingGroupDescriptions(List.of(RatingGroupDescription.builder()
                        .ratingGroupId("992")
                        .description("Facebook Gratis").build()))
                .build()));

        final List<RatingGroupDescription> result = ratingGroupDescriptionManager.getRatingGroupsDescription(RATING_GROUPS_SOCIAL_NETWORKS);

        assertNotNull(result);
        assertEquals("992", result.get(0).getRatingGroupId());
        assertEquals("Facebook Gratis", result.get(0).getDescription());
    }

    @Test
    void testGetRatingGroupsDescriptionWithoutRedisCache() {
        when(ratingGroupDescriptionBO.getRatingGroupsDescription(List.of("5"))).thenReturn(List.of(RatingGroupDescription.builder()
                .ratingGroupId("5")
                .description("Conexion Movil").build()));

        final List<RatingGroupDescription> result = ratingGroupDescriptionManager.getRatingGroupsDescription(List.of("5"));

        assertNotNull(result);
        assertEquals("5", result.get(0).getRatingGroupId());
        assertEquals("Conexion Movil", result.get(0).getDescription());
    }

    @Test
    void testGetRatingGroupsDescriptionWithRedisCache() {
        when(ratingGroupRepository.findById(anyString())).thenReturn(Optional.of(CacheRatingGroupDescription.builder()
                .key("RATING_GROUPS_FILTERS_AR")
                .ratingGroupDescriptions(List.of(RatingGroupDescription.builder()
                        .ratingGroupId("97")
                        .description("Roaming bloque CR PP").build()))
                .build()));

        final List<RatingGroupDescription> result = ratingGroupDescriptionManager.getRatingGroupsDescription(List.of("5", "60", "61"));

        assertNotNull(result);
        assertEquals("97", result.get(0).getRatingGroupId());
        assertEquals("Roaming bloque CR PP", result.get(0).getDescription());
    }

    @Test
    void testValidatedRatingGroupDescriptions() {
        when(prepayParametersManager.getPrepayParameters(RATING_GROUPS_SOCIAL_NETWORKS)).thenReturn(PrepayParameters.builder()
                .name(RATING_GROUPS_SOCIAL_NETWORKS)
                .value("#992#")
                .build());
        when(ratingGroupService.getRatingGroupsDescription(List.of("992")))
                .thenReturn(List.of(RatingGroupDescription.builder()
                        .ratingGroupId("999")
                        .description("Facebook Gratis").build()));

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            ratingGroupDescriptionManager.getRatingGroupsDescription(RATING_GROUPS_SOCIAL_NETWORKS);
        });

        assertEquals("200009", thrown.getCode());
        assertEquals("Error no se encontraron datos cargados en el registro de V_RATING_GROUP_DESCRIPTION con el valor 992",
                thrown.getMessage());

    }

    @Test
    void removeAll() {
        doNothing().when(ratingGroupRepository).deleteAll();
        ratingGroupDescriptionManager.removeAll();
        verify(ratingGroupRepository,times(1)).deleteAll();
    }
}