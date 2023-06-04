package task.apiframework.extensions;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import task.apiframework.api.RestClient;
import task.apiframework.utils.ClientProvider;
import task.apiframework.utils.WebClientLogger;

import static java.lang.System.lineSeparator;
import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

public class TestSetupExtension implements AfterTestExecutionCallback, BeforeTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        final ApplicationContext applicationContext = getApplicationContext(context);
        if (context.getExecutionException().isPresent()) {
            logRequest(applicationContext.getBean(WebClientLogger.class));
            logResponse(applicationContext.getBean(RestClient.class));
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) {
        final ApplicationContext applicationContext = getApplicationContext(extensionContext);
        applicationContext.getBean(RestClient.class)
                .getClient()
                .mutate()
                .defaultHeaders(headers -> headers.addAll(applicationContext
                        .getBean(ClientProvider.class)
                        .prepareHeadersWithoutToken()));
    }

    @Attachment(value = "Response", type = "text/html")
    private String logResponse(final RestClient restClient) {
        final StringBuilder requestDetails = new StringBuilder();
        requestDetails
                .append("RESPONSE STATUS CODE: " + restClient.getResponseEntity().getStatusCodeValue())
                .append(lineSeparator())
                .append("RESPONSE HEADERS: " + restClient.getResponseEntity().getHeaders().toString())
                .append(lineSeparator());
        if (restClient.getResponseEntity().getBody() != null) {
            requestDetails
                    .append("RESPONSE BODY: " + restClient.getResponseEntity().getBody());
        }
        return requestDetails.toString();
    }

    @Attachment(value = "Request", type = "text/html")
    private String logRequest(final WebClientLogger webClientLogger) {
        return webClientLogger.getRequestDetails().toString();
    }
}
