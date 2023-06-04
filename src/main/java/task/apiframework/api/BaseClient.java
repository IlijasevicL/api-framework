package task.apiframework.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public abstract class BaseClient {

    protected RestClient client;

    public BaseClient(final RestClient restClient) {
        client = restClient;
    }

    public void addHeader(final String headerName, final String headerValue) {
        final WebClient newClient = client.getClient().mutate().defaultHeader(headerName, headerValue).build();
        client.setClient(newClient);
    }

    public void removeHeader(final String headerName) {
        final WebClient newClient =
                client.getClient().mutate().defaultHeaders(headers -> headers.remove(headerName)).build();
        client.setClient(newClient);
    }

    public <T> List<T> getListOfObjectsFromJson(final Class<T> clazz) {
        return client.getDataConverter().mapJsonResponseToListOfObjects(
                client.getResponseEntity().getBody(), clazz);
    }

    public <T> WrappedResponse<T> getWrappedResponseObject(final Class<T> clazz) {
        return client.getDataConverter().mapJsonResponseToObject(client.getResponseEntity(), clazz);
    }

    public ResponseEntity<String> getResponseEntity() {
        return client.getResponseEntity();
    }

}