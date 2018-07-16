package sp.sample.mitac.shared;

import com.fasterxml.jackson.databind.JsonNode;

public class UDPCoreConfig {
    private static JsonNode config;

    public static void setConfig(JsonNode config) {
        UDPCoreConfig.config = config;
    }
    public static JsonNode getConfig() {
        return config;
    }
}
