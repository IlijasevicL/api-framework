package task.apiframework.steps;

import io.qameta.allure.Step;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import task.apiframework.annotations.ThreadLocalScope;
import task.apiframework.api.RestClient;
import task.apiframework.api.UnixTimestampConverterClient;
import task.apiframework.api.WrappedResponse;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@Scope(ThreadLocalScope.NAME)
public class UnixTimestampConverterSteps extends BaseStep<UnixTimestampConverterSteps> {

    @Getter
    private final UnixTimestampConverterClient converterClient;

    @Autowired
    public UnixTimestampConverterSteps(final UnixTimestampConverterClient converterClient, final RestClient restClient) {
        super(restClient);
        this.converterClient = converterClient;
    }

    @Step("Convert string date {date} to epoch milis")
    public UnixTimestampConverterSteps convert(final String date) {
        converterClient.convert(date);
        return this;
    }

    @Step("Verify converter response has correct data {expectedResponse}")
    public UnixTimestampConverterSteps verifyConverterResponseIsCorrect(final String expectedResponse) {
        final WrappedResponse<String> response = converterClient.getWrappedResponseObject(String.class);
        assertThat(response.getResponseBody()).isEqualTo(expectedResponse);
        return this;
    }
}
