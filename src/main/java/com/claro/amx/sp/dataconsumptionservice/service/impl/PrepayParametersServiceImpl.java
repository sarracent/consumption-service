package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogService;
import com.claro.amx.sp.dataconsumptionservice.business.bo.PrepayParametersBO;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.service.PrepayParametersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrepayParametersServiceImpl implements PrepayParametersService {
    private final PrepayParametersBO prepayParametersBO;

    @Override
    @LogService
    public PrepayParameters getPrepayParameters(String parameterName) {
        return prepayParametersBO.getPrepayParameters(parameterName);
    }

}
