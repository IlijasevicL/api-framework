package task.apiframework;

import lombok.SneakyThrows;
import task.apiframework.extensions.TestSetupExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
@ExtendWith({TestSetupExtension.class})
public class BaseTest {

    @SneakyThrows
    protected void waitAWhile(final Duration duration) {
        Thread.sleep(duration.toMillis());
    }
}
