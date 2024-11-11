package org.acme;

import java.util.Map;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "secretsmanager-config")
public interface SecretsManagerConfigConfig {

    String endpoint();

    String region();

    String accessKey();

    String secretKey();

    Map<String, String> secrets();
    
}
