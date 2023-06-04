package task.apiframework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import task.apiframework.api.WrappedResponse;
import task.apiframework.exceptions.TestException;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;

@Component
public class DataConverter {

    private ObjectMapper mapper;

    @Autowired
    public DataConverter(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> WrappedResponse<T> mapJsonResponseToObject(final ResponseEntity<String> response, final Class<T> clazz) {
        final WrappedResponse<T> wrappedResponse = new WrappedResponse<>();
        wrappedResponse.setStatus(response.getStatusCode());
        wrappedResponse.setHttpHeaders(response.getHeaders());
        if (!response.hasBody()) {
            return wrappedResponse;
        }
        try {
            wrappedResponse.setResponseBody(mapper.readValue(response.getBody(), clazz));
        } catch (JsonProcessingException e) {
            throw new TestException(e.getMessage(), e);
        }
        return wrappedResponse;
    }

    public <T> List<T> mapJsonResponseToListOfObjects(final String responseAsString, final Class<T> clazz) {
        List<T> result;
        CollectionType typeReference = defaultInstance().constructCollectionType(List.class, clazz);
        try {
            result = mapper.readValue(mapper.readTree(responseAsString).toString(), typeReference);
        } catch (IOException e) {
            throw new TestException(e);
        }
        return result;
    }

    public String createJsonFromObject(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new TestException(e);
        }
    }

    public <T> T convertJsonStringToObject(final String responseAsString, final Class<T> clazz) {
        final T response;
        try {
            response = mapper.readValue(mapper.readTree(responseAsString).toString(), clazz);
        } catch (IOException e) {
            throw new TestException(e);
        }
        return response;
    }
}
