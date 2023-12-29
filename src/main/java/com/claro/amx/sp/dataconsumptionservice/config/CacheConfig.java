package com.claro.amx.sp.dataconsumptionservice.config;

import com.claro.amx.sp.dataconsumptionservice.manager.PrepayParametersManager;
import com.claro.amx.sp.dataconsumptionservice.manager.RatingGroupDescriptionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CacheConfig {
    private final PrepayParametersManager prepayParametersManager;
    private final RatingGroupDescriptionManager ratingGroupDescriptionManager;

    @Scheduled(cron = "${CacheConfig.cron}")
    public void clearCacheSchedule() {
        prepayParametersManager.removeAll();
        ratingGroupDescriptionManager.removeAll();
    }

}
