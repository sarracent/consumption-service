package com.claro.amx.sp.dataconsumptionservice.manager;

import com.claro.amx.sp.dataconsumptionservice.annotations.log.LogManager;
import com.claro.amx.sp.dataconsumptionservice.business.bo.RatingGroupDescriptionBO;
import com.claro.amx.sp.dataconsumptionservice.exception.impl.BusinessException;
import com.claro.amx.sp.dataconsumptionservice.model.cache.CacheRatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import com.claro.amx.sp.dataconsumptionservice.repository.RatingGroupRepository;
import com.claro.amx.sp.dataconsumptionservice.service.RatingGroupService;
import com.claro.amx.sp.dataconsumptionservice.utility.Util;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.HASH;
import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.UNDERSCORE;
import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_VALUE;
import static com.claro.amx.sp.dataconsumptionservice.constants.Errors.ERROR_REDIS_V_RATING_GROUP_DESCRIPTION_MANAGER;

@Component
@RequiredArgsConstructor
public class RatingGroupDescriptionManager {
    @Value("${country}")
    private String country;
    private final RatingGroupService ratingGroupService;
    private final RatingGroupDescriptionBO ratingGroupDescriptionBO;
    private final RatingGroupRepository ratingGroupRepository;
    private final PrepayParametersManager prepayParametersManager;

    @LogManager
    public List<RatingGroupDescription> getRatingGroupsDescription(String parameterName){
        try {
            String redisKey = getKey(parameterName);
            List<RatingGroupDescription> ratingGroupDescriptionsData;
            final List<String> ratingGroupsIds = Util.getValueList(prepayParametersManager
                    .getPrepayParameters(parameterName).getValue(), HASH);

            Optional<CacheRatingGroupDescription> optionalCacheRatingGroupDescription = ratingGroupRepository.findById(redisKey);

            if (optionalCacheRatingGroupDescription.isPresent()) {
                ratingGroupDescriptionsData = optionalCacheRatingGroupDescription.get().getRatingGroupDescriptions();

                validatedRatingGroupDescriptions(ratingGroupDescriptionsData, ratingGroupsIds);
            }
            else {
                ratingGroupDescriptionsData = ratingGroupService.getRatingGroupsDescription(ratingGroupsIds);

                validatedRatingGroupDescriptions(ratingGroupDescriptionsData, ratingGroupsIds);

                ratingGroupRepository.save(new CacheRatingGroupDescription(redisKey, ratingGroupDescriptionsData ));
            }
            return ratingGroupDescriptionsData;

        } catch (HystrixBadRequestException hbre) {
            throw new BusinessException(ERROR_REDIS_V_RATING_GROUP_DESCRIPTION_MANAGER.getCode(),
                    String.format(ERROR_REDIS_V_RATING_GROUP_DESCRIPTION_MANAGER.getMessage(), hbre.getMessage(), hbre));
        }
    }

    private void validatedRatingGroupDescriptions(List<RatingGroupDescription> ratingGroupDescriptionsData, List<String> ratingGroupsIds) {
        ratingGroupsIds.forEach(ratingGroup -> {
            if (!ratingGroupDescriptionsData.stream().map(RatingGroupDescription::getRatingGroupId)
                    .collect(Collectors.toList()).contains(ratingGroup)) {
                throw new BusinessException(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_VALUE.getCode(),
                        String.format(ERROR_DATABASE_V_RATING_GROUP_DESCRIPTION_NOT_VALUE.getMessage(),
                                ratingGroup));
            }
        });
    }

    @LogManager
    public List<RatingGroupDescription> getRatingGroupsDescription(List<String> ratingGroups){
        try {
            String redisKey = getKey(ratingGroups.toString().concat("_RATING_GROUP"));
            List<RatingGroupDescription> ratingGroupDescriptionsData;

            Optional<CacheRatingGroupDescription> optionalCacheRatingGroupDescription = ratingGroupRepository.findById(redisKey);

            if(optionalCacheRatingGroupDescription.isPresent()){
                ratingGroupDescriptionsData = optionalCacheRatingGroupDescription.get().getRatingGroupDescriptions();
            }
            else {
                ratingGroupDescriptionsData = ratingGroupDescriptionBO.getRatingGroupsDescription(ratingGroups);
                ratingGroupRepository.save(new CacheRatingGroupDescription(redisKey, ratingGroupDescriptionsData ));
            }
            return ratingGroupDescriptionsData;

        } catch (HystrixBadRequestException hbre) {
            throw new BusinessException(ERROR_REDIS_V_RATING_GROUP_DESCRIPTION_MANAGER.getCode(),
                    String.format(ERROR_REDIS_V_RATING_GROUP_DESCRIPTION_MANAGER.getMessage(), hbre.getMessage(), hbre));
        }
    }

    public void removeAll() {
        ratingGroupRepository.deleteAll();
    }

    private String getKey(String parameter) {
        return parameter.concat(UNDERSCORE + country);
    }

}
