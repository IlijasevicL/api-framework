package task.apiframework.utils;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import task.apiframework.annotations.ThreadLocalScope;

import java.net.URI;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Scope(ThreadLocalScope.NAME)
public class ClientProvider {

    private WebClientLogger webClientLogger;

    @Autowired
    public ClientProvider(final WebClientLogger webClientLogger) {
        this.webClientLogger = webClientLogger;
    }

    public HttpHeaders prepareHeadersWithoutToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        headers.add(Constants.CLIENT_TYPE, "web");
        headers.add(Constants.CLIENT_VERSION, "1.0");
        return headers;
    }

    public HttpClient prepareClient() {
        return new HttpClient(new SslContextFactory.Client()) {
            @Override
            public Request newRequest(final URI uri) {
                Request request = super.newRequest(uri);
                return webClientLogger.enhance(request);
            }
        };
    }
}
