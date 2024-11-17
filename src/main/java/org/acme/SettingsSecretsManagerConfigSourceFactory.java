package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.OptionalInt;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public class SettingsSecretsManagerConfigSourceFactory
        implements ConfigSourceFactory.ConfigurableConfigSourceFactory<SecretsManagerConfigConfig> {

    private static final int ORDINAL = 270; // this is higher than the file system or jar ordinals, but lower than env
                                            // vars

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context, SecretsManagerConfigConfig config) {
        try {
            var client = build(config);
            return Collections
                    .singletonList(new SettingsSecretsManagerConfigSource(client, config.secrets(), ORDINAL));
        }
        catch(Exception ex){
            return Collections.emptyList();
        }
    }

    SecretsManagerClient build(SecretsManagerConfigConfig config) throws RuntimeException {
        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .endpointOverride(new URI(config.endpoint()))
                .region(Region.of(config.region()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials
                        .create(config.accessKey(), config.secretKey())))
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build()) {
            return client;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OptionalInt getPriority() {
        return OptionalInt.of(ORDINAL);
    }
}
