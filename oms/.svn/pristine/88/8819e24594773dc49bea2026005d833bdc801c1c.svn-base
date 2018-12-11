package com.iwhalecloud.retail.oms.web.controller.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {
    private int    expireSeconds;
    private String  clusterNodes;
    private int    commandTimeout;
}
