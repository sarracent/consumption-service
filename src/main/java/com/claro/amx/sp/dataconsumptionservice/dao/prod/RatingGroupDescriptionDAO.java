package com.claro.amx.sp.dataconsumptionservice.dao.prod;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogDAO;
import com.claro.amx.sp.dataconsumptionservice.config.MyBatisConfig;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.DataBaseException;
import com.claro.amx.sp.dataconsumptionservice.mapper.prod.RatingGroupDescriptionMapper;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
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
public class RatingGroupDescriptionDAO {

    @Value("${timeout.rating.group.description}")
    private Integer timeout;
    @Qualifier(MyBatisConfig.PROD_SESSION_FACTORY)
    private final SqlSessionFactoryBean sessionFactoryBean;

    @LogDAO
    public List<RatingGroupDescription> getRatingGroupDescriptionData(List<String> ratingGroups) {
        try (SqlSession sqlSession = Objects.requireNonNull(sessionFactoryBean.getObject()).openSession()) {
            sqlSession.getConfiguration().setDefaultStatementTimeout(timeout);
            return sqlSession.getMapper(RatingGroupDescriptionMapper.class).getRatingGroups(ratingGroups);
        }
        catch (PersistenceException ex) {
            if (ex.getCause() instanceof SQLTimeoutException) {
                throw new DataBaseException(ERROR_DATABASE_TIMEOUT.getCode(),
                        String.format(ERROR_DATABASE_TIMEOUT.getMessage(), ex.getMessage(), ex));
            } else {
                throw new DataBaseException(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION.getCode(),
                        String.format(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION.getMessage(), ex.getMessage(), ex));
            }
        }
        catch (Exception e) {
            throw new DataBaseException(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION.getCode(),
                    String.format(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION.getMessage(), e.getMessage(), e));
        }
    }

}