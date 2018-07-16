package sp.sample.mitac.shared;

import com.fasterxml.jackson.databind.JsonNode;

public class HttpCoreConfig {
    private static JsonNode config;

    public static void setConfig(JsonNode config) {
        HttpCoreConfig.config = config;
    }
    public static JsonNode getConfig() {
        return config;
    }
}
