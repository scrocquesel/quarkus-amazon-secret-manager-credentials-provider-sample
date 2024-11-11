package org.acme;

import java.util.Map;
import java.util.Set;

import io.smallrye.config.common.AbstractConfigSource;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public class SettingsSecretsManagerConfigSource extends AbstractConfigSource {

    private SecretsManagerClient secretsManagerClient;
    private Map<String, String> secrets;

    public SettingsSecretsManagerConfigSource(SecretsManagerClient secretsManagerClient,
            Map<String, String> secrets, int ordinal) {
        super("SecretsManagerConfigSource", ordinal);
        this.secretsManagerClient = secretsManagerClient;
        this.secrets = secrets;
    }

    @Override
    public Set<String> getPropertyNames() {
        return secrets.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        if(!secrets.containsKey(propertyName)) return null;
        return  secretsManagerClient.getSecretValue(builder -> builder.secretId(secrets.get(propertyName))).secretString();
    }
}
