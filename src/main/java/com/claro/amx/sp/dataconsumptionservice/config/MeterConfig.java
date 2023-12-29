package com.claro.amx.sp.dataconsumptionservice.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterConfig {

    @Bean
    public MeterFilter meterFilter() {
        return new MeterFilter() {
            @Override
            public MeterFilterReply accept(Meter.Id id) {
                if (id.getName().startsWith("tomcat.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("jvm.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("process.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("system.")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("resilience4j")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("logback")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("disk")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("executor")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("http")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("spring")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("cache")) {
                    return MeterFilterReply.DENY;
                }
                if (id.getName().startsWith("hikaricp")) {
                    return MeterFilterReply.DENY;
                }
                return MeterFilterReply.NEUTRAL;
            }
        };
    }
}
