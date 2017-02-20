package me.jcala.xmarket.server.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jpush config
 * @author cuihao
 */
@Data
@Component
@ConfigurationProperties(prefix="jpush")
public class JPushConfig {
    private String appKey;
    private String masterSecret;
}
