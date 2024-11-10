package org.acme;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.arc.Unremovable;
import io.quarkus.credentials.CredentialsProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

/**
 * Implements CredentialsProvider interface
 */
@ApplicationScoped
@Unremovable
@Named("aws-secret-manager-credentials-provider")
public class AwsSecretManagerCredentialsProvider implements CredentialsProvider {

    @Inject
    SecretsManagerClient client;

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {
        
        String user = client.getSecretValue(builder -> builder.secretId("pgsql/user")).secretString();
        String password = client.getSecretValue(builder -> builder.secretId("pgsql/password")).secretString();


        Map<String, String> properties = new HashMap<>();
        properties.put(USER_PROPERTY_NAME, user);
        properties.put(PASSWORD_PROPERTY_NAME, password);
        return properties;

    }
    
}
