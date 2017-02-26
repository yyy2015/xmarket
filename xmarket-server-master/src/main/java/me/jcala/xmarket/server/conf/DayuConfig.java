package me.jcala.xmarket.server.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Alidayu app secret config
 * @author cuihao
 */
@Data
@Component
@ConfigurationProperties(prefix="dayu")
public class DayuConfig {
    private String appKey;
    private String appSecret;
    private String templateId;
}
