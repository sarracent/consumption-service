package com.claro.amx.sp.dataconsumptionservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisConfig {
    private List<String> nodes;
    private String userName;
    private String password;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){

        RedisClusterConfiguration clusterConf =
                new RedisClusterConfiguration(
                        nodes);
        clusterConf.setUsername(userName);
        clusterConf.setPassword(password);

        return new LettuceConnectionFactory(clusterConf);
    }
}
