package com.claro.amx.sp.dataconsumptionservice.model.cache;


import com.claro.amx.sp.dataconsumptionservice.model.prod.RatingGroupDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.DESCRIPTION_CACHE;
import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.TIME_TO_LIVE;

@Data
@AllArgsConstructor
@Getter
@Builder
@RedisHash(value = DESCRIPTION_CACHE, timeToLive = TIME_TO_LIVE)//change timeToLive to prod
public class CacheRatingGroupDescription {
    @Id
    String key;
    List<RatingGroupDescription> ratingGroupDescriptions;
}
