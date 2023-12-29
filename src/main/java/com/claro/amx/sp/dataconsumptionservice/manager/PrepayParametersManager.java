package com.claro.amx.sp.dataconsumptionservice.manager;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogManager;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.cache.CachePrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.model.ccard.PrepayParameters;
import com.claro.amx.sp.dataconsumptionservice.repository.PrepayParametersRepository;
import com.claro.amx.sp.dataconsumptionservice.service.PrepayParametersService;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.UNDERSCORE;
import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_REDIS_PREPAY_PARAMETERS_MANAGER;

@Component
@RequiredArgsConstructor
public class PrepayParametersManager {
    @Value("${country}")
    private String country;
    private final PrepayParametersService prepayParametersService;
    private final PrepayParametersRepository parametersRepository;

    @LogManager
    public PrepayParameters getPrepayParameters(String parameterName){
        try {
            String redisKey = getKey(parameterName);
            PrepayParameters prepayParameters;

            Optional<CachePrepayParameters> optionalCachePrepayParameters = parametersRepository.findById(redisKey);

            if (optionalCachePrepayParameters.isPresent()) {
                prepayParameters = optionalCachePrepayParameters.get().getPrepayParameters();
            }
            else {
                prepayParameters = prepayParametersService.getPrepayParameters(parameterName);
                parametersRepository.save(new CachePrepayParameters(redisKey, prepayParameters));
            }
            return prepayParameters;

        } catch (HystrixBadRequestException hbre) {
            throw new BusinessException(ERROR_REDIS_PREPAY_PARAMETERS_MANAGER.getCode(),
                    String.format(ERROR_REDIS_PREPAY_PARAMETERS_MANAGER.getMessage(), hbre.getMessage(), hbre));
        }
    }

    public void removeAll() {
        parametersRepository.deleteAll();
    }

    private String getKey(String parameter) {
        return parameter.concat(UNDERSCORE + country);
    }

}
