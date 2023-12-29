package com.claro.amx.sp.dataconsumptionservice.dao;

import com.claro.amx.sp.dataconsumptionservice.dao.ccard.PrepayParametersDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import com.claro.amx.sp.dataconsumptionservice.mapper.ccard.PrepayParametersMapper;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.util.List;
import java.util.Objects;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.RATING_GROUPS_FILTERS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrepayParametersDAOTest {
    @Mock
    private PrepayParametersMapper mapper;
    @Mock
    private SqlSessionFactoryBean mockedSessionFactoryBean;
    @Mock
    private SqlSessionFactory mockedSessionFactory;
    @Mock
    private SqlSession mockedSession;
    private PrepayParametersDAO parametersDAO;

    @BeforeEach
    void setUp() throws Exception {
        when(mockedSession.getMapper(PrepayParametersMapper.class)).thenReturn(mapper);
        when(mockedSession.getConfiguration()).thenReturn(new Configuration());
        when(mockedSessionFactoryBean.getObject()).thenReturn(mockedSessionFactory);
        when(Objects.requireNonNull(mockedSessionFactoryBean.getObject()).openSession()).thenReturn(mockedSession);

        parametersDAO = new PrepayParametersDAO(mockedSessionFactoryBean);
    }

    @Test
    void testGetPrepayParametersDataOK() {
        when(mapper.getPrepayParametersData(RATING_GROUPS_FILTERS)).thenReturn(List.of(PrepayParameters.builder()
                .name(RATING_GROUPS_FILTERS)
                .value("#5#50#60#61#80#97#991#996#")
                .build()));

        final List<PrepayParameters> result = parametersDAO.getPrepayParametersData(RATING_GROUPS_FILTERS);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(RATING_GROUPS_FILTERS, result.get(0).getName());
        assertEquals("#5#50#60#61#80#97#991#996#", result.get(0).getValue());
    }

    @Test
    void testGetPrepayParametersDataError() {
        when(mapper.getPrepayParametersData(anyString())).thenThrow(DataBaseException.class);

        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            parametersDAO.getPrepayParametersData(null);
        });

        assertEquals("200005", thrown.getCode());
        assertTrue(thrown.getMessage().contains("Error al consultar la base de datos CCARD PrepayParametersDAO"));
    }

    @Test
    void testGetPrepayParametersDataPersistenceExceptionError() {
        when(mapper.getPrepayParametersData(RATING_GROUPS_FILTERS)).thenThrow(PersistenceException.class);

        DataBaseException thrown = assertThrows(DataBaseException.class, () -> {
            parametersDAO.getPrepayParametersData(RATING_GROUPS_FILTERS);
        });

        assertEquals("200005", thrown.getCode());
    }
}