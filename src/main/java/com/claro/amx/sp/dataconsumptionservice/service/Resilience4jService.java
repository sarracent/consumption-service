package com.claro.amx.sp.dataconsumptionservice.service;

import java.util.function.Supplier;

public interface Resilience4jService {
    <T> T executeFilterList(Supplier<T> operation);
    <T> T executeDataConsumption(Supplier<T> operation);
}
