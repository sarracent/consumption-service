package com.claro.amx.sp.dataconsumptionservice.service.impl;

import com.claro.amx.sp.dataconsumptionservice.service.Resilience4jService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class Resilience4jServiceImpl implements Resilience4jService {

    private static final String FILTER_LIST_API = "filterlist";
    private static final String DATA_CONSUMPTION_API = "dataconsumption";


    @Override
    @CircuitBreaker(name = FILTER_LIST_API)
    @RateLimiter(name = FILTER_LIST_API)
    @Bulkhead(name = FILTER_LIST_API)
    @Retry(name = FILTER_LIST_API)
    public <T> T executeFilterList(Supplier<T> operation) {
        return operation.get();
    }

    @Override
    @CircuitBreaker(name = DATA_CONSUMPTION_API)
    @RateLimiter(name = DATA_CONSUMPTION_API)
    @Bulkhead(name = DATA_CONSUMPTION_API)
    @Retry(name = DATA_CONSUMPTION_API)
    public <T> T executeDataConsumption(Supplier<T> operation) {
        return operation.get();
    }


}
