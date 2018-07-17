package sp.sample.mitac.infra.tools;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBConnector {

    public static MongoDBConnector create(ConfigBuilder builder) {
        return new MongoDBConnector(builder);
    }

    public static MongoDBConnector create(String uri, String dbName) {
        return new MongoDBConnector(new MongoDBConnector.ConfigBuilder()
                .withUri(uri)
                .withDbName(dbName)
        );
    }

    private MongoClient dbClient;
    private String dbName;
    private MongoDBConnector(ConfigBuilder builder) {
        this.dbClient = new MongoClient(new MongoClientURI(builder.uri));
        this.dbName = builder.dbName;
    }

    public MongoClient getDbClient() {
        return dbClient;
    }

    public String getDbName() {
        return dbName;
    }

    public static class ConfigBuilder {
        private String uri;
        private String dbName;

        public ConfigBuilder withUri(String uri) {
            this.uri = uri;
            return this;
        }
        public ConfigBuilder withDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }
    }
}
