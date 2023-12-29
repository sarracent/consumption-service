package configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Clase encargada de levantar el Bean MeterRegistry ya que no se puede mockear
 */
@TestConfiguration
public class TestConfigurationMeter {
    @Bean
    public MeterRegistry registry() {
        return new SimpleMeterRegistry();
    }
}
