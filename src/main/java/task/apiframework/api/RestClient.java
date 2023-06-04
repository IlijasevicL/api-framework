package task.apiframework.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import task.apiframework.annotations.ThreadLocalScope;
import task.apiframework.utils.DataConverter;

import static java.net.URI.create;

@Component
@Scope(ThreadLocalScope.NAME)
public class RestClient {

    @Getter
    @Setter
    private WebClient client;
    @Getter
    private ResponseEntity<String> responseEntity;
    @Getter
    private final DataConverter dataConverter;
    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    public RestClient(final WebClient client, final DataConverter dataConverter) {
        this.client = client;
        this.dataConverter = dataConverter;
    }

    public void postRequest(final String url, final Object body) {
        responseEntity = client.post()
                .uri(create(baseUrl + url))
                .bodyValue(body)
                .exchangeToMono(this::toEntity)
                .block();
    }

    public void getRequest(final String url) {
        responseEntity = client.get()
                .uri(create(baseUrl + url))
                .exchangeToMono(this::toEntity)
                .block();
    }

    public void deleteRequest(final String url) {
        responseEntity = client.delete()
                .uri(create(baseUrl + url))
                .exchangeToMono(this::toEntity)
                .block();
    }

    public void putRequest(final String url, final Object body) {
        responseEntity = client.put()
                .uri(create(baseUrl + url))
                .bodyValue(body)
                .exchangeToMono(this::toEntity)
                .block();
    }

    public void addHeader(final String headerName, final String headerValue) {
        client = client.mutate().defaultHeader(headerName, headerValue).build();
    }

    public void removeHeader(final String headerName) {
        client = client.mutate().defaultHeaders(headers -> headers.remove(headerName)).build();
    }

    private Mono<ResponseEntity<String>> toEntity(final ClientResponse response) {
        return response.toEntity(String.class);
    }
}