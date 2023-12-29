package com.claro.amx.sp.dataconsumptionservice.dao.ccard;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogDAO;
import com.claro.amx.sp.dataconsumptionservice.config.MyBatisConfig;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import com.claro.amx.sp.dataconsumptionservice.mapper.ccard.DataConsumptionMapper;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.DataDailyConsumption;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.sql.SQLTimeoutException;

import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_DATABASE_TIMEOUT;
import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_DATABASE_DATA_CONSUMPTION;

@Component
@RequiredArgsConstructor
public class DataConsumptionDAO {

    @Value("${timeout.data.consumption}")
    private Integer timeout;
    @Qualifier(MyBatisConfig.CCARD_SESSION_FACTORY)
    private final SqlSessionFactoryBean sessionFactoryBean;

    @LogDAO
    public List<DataDailyConsumption> getDataConsumption(String billNumber, String keyPartition, String dateFrom,
                                                         String dateTo, List<String> ratingGroups, String roaming) {
        try (SqlSession sqlSession = Objects.requireNonNull(sessionFactoryBean.getObject()).openSession()) {
            sqlSession.getConfiguration().setDefaultStatementTimeout(timeout);
            return sqlSession.getMapper(DataConsumptionMapper.class).getDataConsumption(billNumber, keyPartition, dateFrom,
                    dateTo, ratingGroups, roaming);
        }
        catch (PersistenceException ex) {
            if (ex.getCause() instanceof SQLTimeoutException) {
                throw new DataBaseException(ERROR_DATABASE_TIMEOUT.getCode(),
                        String.format(ERROR_DATABASE_TIMEOUT.getMessage(), ex.getMessage(), ex));
            } else {
                throw new DataBaseException(ERROR_DATABASE_DATA_CONSUMPTION.getCode(),
                        String.format(ERROR_DATABASE_DATA_CONSUMPTION.getMessage(), ex.getMessage(), ex));
            }
        }
        catch (Exception ex) {
            throw new DataBaseException(ERROR_DATABASE_DATA_CONSUMPTION.getCode(),
                    String.format(ERROR_DATABASE_DATA_CONSUMPTION.getMessage(), ex.getMessage(), ex));
        }
    }

}
