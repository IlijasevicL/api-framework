package task.apiframework.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.yaml.snakeyaml.Yaml;
import task.apiframework.annotations.ThreadLocalScope;
import task.apiframework.utils.ClientProvider;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.lang.System.getProperty;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Scope(ThreadLocalScope.NAME)
    public WebClient client(final ClientProvider clientProvider) {
        return WebClient.builder()
                .clientConnector(new JettyClientHttpConnector(clientProvider.prepareClient()))
                .build();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public Yaml yaml() {
        return new Yaml();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        final String environment = getProperty("env") == null ? "dev" : getProperty("env");
        configurer
                .setLocations(
                        new ClassPathResource(format("environment/%s.properties", environment)),
                        new ClassPathResource("application.properties"));
        return configurer;
    }

    @Bean
    public static ThreadLocalScope threadLocalScope() {
        return new ThreadLocalScope();
    }

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer(final ThreadLocalScope threadLocalScope) {
        CustomScopeConfigurer configure = new CustomScopeConfigurer();
        Map<String, Object> scopes = new HashMap<>();
        scopes.put(ThreadLocalScope.NAME, threadLocalScope);
        configure.setScopes(scopes);
        return configure;
    }
}
