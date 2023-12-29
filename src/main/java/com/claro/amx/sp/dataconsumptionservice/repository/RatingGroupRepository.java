package com.claro.amx.sp.dataconsumptionservice.repository;

import com.claro.amx.sp.dataconsumptionservice.model.cache.CacheRatingGroupDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingGroupRepository extends CrudRepository<CacheRatingGroupDescription, String> {
}
