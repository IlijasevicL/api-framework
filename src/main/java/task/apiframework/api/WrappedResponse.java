package task.apiframework.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrappedResponse<T> {

    private HttpStatus status;
    private HttpHeaders httpHeaders;
    private T responseBody;
}
