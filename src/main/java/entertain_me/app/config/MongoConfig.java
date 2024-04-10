package entertain_me.app.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${mongodb.host}")
    private String mongoHost;

    @Value("${mongodb.username}")
    private String mongoUsername;

    @Value("${mongodb.password}")
    private String mongoPassword;

    @Value("${mongodb.database}")
    private String mongoDatabase;

    @Bean
    MongoClient mongoClient() {
        String connectionString = "mongodb+srv://" + mongoUsername + ":" + mongoPassword + "@" + mongoHost;

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoDatabase);
    }
}

