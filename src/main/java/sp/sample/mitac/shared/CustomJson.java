package sp.sample.mitac.shared;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import sp.sample.mitac.shared.interfaces.IJsonClient;

import java.io.IOException;

public class CustomJson implements IJsonClient {

    public static CustomJson defaultSerializer() {
        ObjectMapper m = new ObjectMapper();
        VisibilityChecker checker = m.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY);
        m.setVisibility(checker);
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return new CustomJson(m);
    }
    public static CustomJson customSerializer(ObjectMapper mapper) {
        return new CustomJson(mapper);
    }
    private ObjectMapper mapper;
    private CustomJson(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T fromJson(String jsonString, Class<T> typeRef) throws CustomException {
        try {
            return this.mapper.readValue(jsonString, typeRef);
        } catch (IOException ex) {
            throw new CustomException(
                    CustomCode.PARSE_MODEL_ERROR.getCode(),
                    ex.getMessage()
            );
        }
    }

    @Override
    public String toString(Object object) throws CustomException {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new CustomException(
                    CustomCode.STRINGNITY_JSON_ERROR.getCode(),
                    ex.getMessage()
            );
        }
    }
}
