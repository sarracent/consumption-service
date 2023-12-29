package com.claro.amx.sp.dataconsumptionservice.business.bo.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogBO;
import com.claro.amx.sp.dataconsumptionservice.business.bo.PrepayParametersBO;
import com.claro.amx.sp.dataconsumptionservice.dao.ccard.PrepayParametersDAO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.utility.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.*;


@Component
@AllArgsConstructor
public class PrepayParametersBOImpl implements PrepayParametersBO {

    private final PrepayParametersDAO prepayParametersDAO;

    @LogBO
    @Override
    public PrepayParameters getPrepayParameters(String parameter) {
        final List<PrepayParameters> prepayParameters = prepayParametersDAO.getPrepayParametersData(parameter);
        if (prepayParameters == null || prepayParameters.isEmpty())
            throw new BusinessException(ERROR_DATABASE_PARAMETERS_NOT_FOUND.getCode(), String.format(ERROR_DATABASE_PARAMETERS_NOT_FOUND.getMessage(), parameter));
        if (prepayParameters.size() > 1)
            throw new BusinessException(ERROR_DATABASE_PARAMETERS_DUPLICATE.getCode(), String.format(ERROR_DATABASE_PARAMETERS_DUPLICATE.getMessage(), parameter));
        if (Util.isNullOrEmpty(prepayParameters.get(0).getValue()))
            throw new BusinessException(ERROR_DATABASE_PARAMETERS_NOT_VALUE.getCode(), String.format(ERROR_DATABASE_PARAMETERS_NOT_VALUE.getMessage(), parameter));

        return prepayParameters.get(0);
    }

}
