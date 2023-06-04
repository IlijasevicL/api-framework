package task.apiframework.steps;

import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import task.apiframework.api.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseStep<T> {

    @Autowired
    private RestClient restClient;

    public BaseStep(final RestClient restClient) {
        this.restClient = restClient;
    }

    @Step("Verify status code 2xx")
    @SuppressWarnings("unchecked")
    public T verifyStatusCode2xx() {
        assertThat(restClient.getResponseEntity().getStatusCodeValue())
                .isBetween(200, 299);
        return (T) this;
    }

    @Step("Verify status code 3xx")
    @SuppressWarnings("unchecked")
    public T verifyStatusCode3xx() {
        assertThat(restClient.getResponseEntity().getStatusCodeValue())
                .isBetween(300, 399);
        return (T) this;
    }

    @Step("Verify status code 4xx")
    @SuppressWarnings("unchecked")
    public T verifyStatusCode4xx() {
        assertThat(restClient.getResponseEntity().getStatusCodeValue())
                .isBetween(400, 499);
        return (T) this;
    }

    @Step("Verify status code 5xx")
    @SuppressWarnings("unchecked")
    public T verifyStatusCode5xx() {
        assertThat(restClient.getResponseEntity().getStatusCodeValue())
                .isBetween(500, 599);
        return (T) this;
    }

    @Step("Verify response body has message {message}")
    @SuppressWarnings("unchecked")
    public T verifyResponseBodyHasMessage(final String message) {
        assertThat(restClient.getResponseEntity().getBody()).contains(message);
        return (T) this;
    }
}
