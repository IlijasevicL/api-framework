package task.apiframework.tests;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import task.apiframework.BaseTest;
import task.apiframework.categories.ParameterizedConverterTest;
import task.apiframework.steps.UnixTimestampConverterSteps;

import java.time.Duration;

public class UnixTimestampConverterTests extends BaseTest {

    @Autowired
    private UnixTimestampConverterSteps converterSteps;

    @BeforeEach
    public void init() {
        // API tests are executed too quickly, we get 429 response, added wait to slow down execution for a period
        waitAWhile(Duration.ofMillis(250));
    }

    @ParameterizedConverterTest
    @DisplayName("Date to timestamp conversion tests")
    @TmsLink("Ticket number where this was implemented")
    @ValueSource(strings = {
            "02/17/2009",
            "17/02/2009",
            "2009/02/17",
            "February 17, 2009",
            "2/17/2009",
            "17/2/2009",
            "2009/2/17",
            " 2/17/2009",
            "17/ 2/2009",
            "2009/ 2/17",
            "02172009",
            "17022009",
            "20090217",
            "Feb172009",
            "17Feb2009",
            "2009Feb17",
            "17 February, 2009",
            "2009, February 17",
            "Feb 17, 2009",
            "17 Feb, 2009",
            "2009, Feb 17",
            "Feb 17, 2009",
            "17 Feb, 2009",
            "2009, Feb 17",
            "02/17/2009 00:00:00 AM",
            "02/17/2009 00:00:00"
    })
    public void dateToTimestampConversion(final String param) {
        final var expected = "1234828800";
        converterSteps
                .convert(param)
                .verifyStatusCode2xx()
                .verifyConverterResponseIsCorrect(expected);
    }

    @ParameterizedConverterTest
    @DisplayName("Timestamp to date conversion tests")
    @TmsLink("Ticket number where this was implemented")
    @CsvSource(value = {
            "1234828800;2009-02-17 00:00:00",
            "-1234828800;1930-11-15 00:00:00",
            "0;1970-01-01 00:00:00",
            "1456704000;2016-02-29 00:00:00"

    }, delimiter = ';')
    public void timestampToDateConversion(final String param, final String expected) {
        converterSteps
                .convert(param)
                .verifyStatusCode2xx()
                .verifyConverterResponseIsCorrect(expected);
    }

    @ParameterizedConverterTest
    @ValueSource(strings = {
            "asdasd",
            "zxc2009-02-17dsf",
            "!@#2009-02-17pps",
            "!#$(",
            "null",
            "undefined"
    })
    @DisplayName("Invalid converter input tests")
    @TmsLink("Ticket number where this was implemented")
    public void invalidConverterInputs(final String param) {
        converterSteps
                .convert(param)
                // API should return error, some other message should be displayed
                .verifyStatusCode4xx()
                .verifyConverterResponseIsCorrect(Boolean.FALSE.toString());
    }
}
