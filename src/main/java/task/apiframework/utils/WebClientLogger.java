package task.apiframework.utils;

import lombok.Getter;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import task.apiframework.annotations.ThreadLocalScope;

import java.nio.charset.StandardCharsets;

import static java.lang.System.lineSeparator;

@Component
@Scope(ThreadLocalScope.NAME)
public class WebClientLogger {

    private static final Logger logger = LoggerFactory.getLogger(WebClientLogger.class);
    @Getter
    private final StringBuilder requestDetails;

    public WebClientLogger() {
        requestDetails = new StringBuilder();
    }

    public Request enhance(final Request request) {
        final StringBuilder group = new StringBuilder();
        onRequestBegin(request, group, requestDetails);
        onRequestHeaders(request, group, requestDetails);
        onRequestContent(request, group, requestDetails);
        onRequestSuccess(request, group);
        group.append(lineSeparator());
        onResponseBegin(request, group);
        onResponseHeaders(request, group);
        onResponseContent(request, group);
        onResponseSuccess(request, group);
        onResponseFailure(request, group);
        return request;
    }

    private void onRequestBegin(final Request request, final StringBuilder group, final StringBuilder requestDetails) {
        request.onRequestBegin(theRequest -> {
            // append request url and method to group
            group
                    .append("REQUEST URL: " + theRequest.getURI().toString())
                    .append(lineSeparator())
                    .append("REQUEST METHOD: " + theRequest.getMethod())
                    .append(lineSeparator());
            requestDetails
                    .append("REQUEST URL: " + theRequest.getURI().toString())
                    .append(lineSeparator())
                    .append("REQUEST METHOD: " + theRequest.getMethod())
                    .append(lineSeparator());
        });
    }

    private void onRequestHeaders(final Request request, final StringBuilder group, final StringBuilder requestDetails) {
        request.onRequestHeaders(theRequest -> {
            group.append("REQUEST HEADERS: ");
            for (HttpField header : theRequest.getHeaders()) {
                // append request headers to group
                group
                        .append(header.getName())
                        .append(":")
                        .append(header.getValue())
                        .append(", ");
            }
            group.append(lineSeparator());
            requestDetails.append("REQUEST HEADERS: ");
            for (HttpField header : theRequest.getHeaders()) {
                // append request headers to group
                requestDetails
                        .append(header.getName())
                        .append(":")
                        .append(header.getValue())
                        .append(", ");
            }
            requestDetails.append(lineSeparator());
        });
    }

    private void onRequestContent(final Request request, final StringBuilder group, final StringBuilder requestDetails) {
        request.onRequestContent((theRequest, content) -> {
            // append content to group
            String bufferAsString = StandardCharsets.UTF_8.decode(content).toString();
            group
                    .append("REQUEST BODY: " + bufferAsString)
                    .append(lineSeparator());
            requestDetails
                    .append("REQUEST BODY: " + bufferAsString)
                    .append(lineSeparator());
        });
    }

    private void onRequestSuccess(final Request request, final StringBuilder group) {
        request.onRequestSuccess(theRequest -> {
            logger.info(group.toString());
            System.out.println("-------------------------------------------------------------------------------------------");
            group.delete(0, group.length());
        });
    }

    private void onResponseBegin(final Request request, final StringBuilder group) {
        request.onResponseBegin(theResponse -> {
            // append response status to group
            group
                    .append(lineSeparator())
                    .append("RESPONSE STATUS: " + theResponse.getStatus())
                    .append(lineSeparator());
        });
    }

    private void onResponseHeaders(final Request request, final StringBuilder group) {
        request.onResponseHeaders(theResponse -> {
            group.append("RESPONSE HEADERS: ");
            for (HttpField header : theResponse.getHeaders()) {
                // append response headers to group
                group
                        .append(header.getName())
                        .append(":")
                        .append(header.getValue())
                        .append(", ");
            }
            group.append(lineSeparator());
        });
    }

    private void onResponseContent(final Request request, final StringBuilder group) {
        request.onResponseContent((theResponse, content) -> {
            String bufferAsString = StandardCharsets.UTF_8.decode(content).toString();
            group
                    .append("RESPONSE BODY: " + bufferAsString)
                    .append(lineSeparator());
        });
    }

    private void onResponseSuccess(final Request request, final StringBuilder group) {
        request.onResponseSuccess(theResponse -> {
            logger.info(group.toString());
            System.out.println("-------------------------------------------------------------------------------------------");
        });
        requestDetails.delete(0, requestDetails.length());
    }

    private void onResponseFailure(final Request request, final StringBuilder group) {
        request.onResponseFailure((response, throwable) -> {
            logger.info(group.toString());
            System.out.println("-------------------------------------------------------------------------------------------");
        });
    }
}
