package task.apiframework.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import task.apiframework.annotations.ThreadLocalScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Scope(ThreadLocalScope.NAME)
public class UnixTimestampConverterClient extends BaseClient {

    private static final String CONVERT_URL = "/api/unix-timestamp-converter/?cached&s=%s";

    public UnixTimestampConverterClient(final RestClient restClient) {
        super(restClient);
    }

    public void convert(final String date) {
        client.getRequest(String.format(CONVERT_URL, URLEncoder.encode(date, StandardCharsets.UTF_8)));
    }
}
