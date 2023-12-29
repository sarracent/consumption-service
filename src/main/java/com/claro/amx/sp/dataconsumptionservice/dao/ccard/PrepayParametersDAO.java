package com.claro.amx.sp.dataconsumptionservice.dao.ccard;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogDAO;
import com.claro.amx.sp.dataconsumptionservice.config.MyBatisConfig;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import com.claro.amx.sp.dataconsumptionservice.mapper.ccard.PrepayParametersMapper;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLTimeoutException;
import java.util.List;
import java.util.Objects;

import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.*;

@Component
@RequiredArgsConstructor
public class PrepayParametersDAO {

    @Value("${timeout.prepay.parameters}")
    private Integer timeout;
    @Qualifier(MyBatisConfig.CCARD_SESSION_FACTORY)
    private final SqlSessionFactoryBean sessionFactoryBean;

    @LogDAO
    public List<PrepayParameters> getPrepayParametersData(String parameterName) {
        try (SqlSession sqlSession = Objects.requireNonNull(sessionFactoryBean.getObject()).openSession()) {
            sqlSession.getConfiguration().setDefaultStatementTimeout(timeout);
            return sqlSession.getMapper(PrepayParametersMapper.class).getPrepayParametersData(parameterName);
        }
        catch (PersistenceException ex) {
            if (ex.getCause() instanceof SQLTimeoutException) {
                throw new DataBaseException(ERROR_DATABASE_TIMEOUT.getCode(),
                        String.format(ERROR_DATABASE_TIMEOUT.getMessage(), ex.getMessage(), ex));
            } else {
                throw new DataBaseException(ERROR_DATABASE_PARAMETERS.getCode(),
                        String.format(ERROR_DATABASE_PARAMETERS.getMessage(), ex.getMessage(), ex));
            }
        }
        catch (Exception e) {
            throw new DataBaseException(ERROR_DATABASE_PARAMETERS.getCode(),
                    String.format(ERROR_DATABASE_PARAMETERS.getMessage(), e.getMessage(), e));
        }
    }
}
