package com.claro.amx.sp.dataconsumptionservice.repository;

import com.claro.amx.sp.dataconsumptionservice.model.cache.CachePrepayParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrepayParametersRepository extends CrudRepository<CachePrepayParameters, String> {
}
