package entertain_me.app.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Log4j2
@Configuration
public class DatabaseConfig {

    @Value("${postgres.host}")
    private String postgresHost;

    @Value("${postgres.port}")
    private String postgresPort;

    @Value("${postgres.username}")
    private String postgresUsername;

    @Value("${postgres.password}")
    private String postgresPassword;

    @Value("${postgres.database}")
    private String postgresDatabase;

    private DriverManagerDataSource dataSource;

    @PostConstruct
    public void init() {
        try {
            dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://" + postgresHost + ":" + postgresPort + "/" + postgresDatabase);
            dataSource.setUsername(postgresUsername);
            dataSource.setPassword(postgresPassword);

            dataSource.getConnection().isValid(1);
        } catch (Exception e) {
            log.error("Connection failed: "+e.getMessage());
            dataSource = null;
        }
    }

    @Bean
    public DataSource dataSource() {
        if (dataSource == null) {
            log.error("Database can't be connected, closing application");
            System.exit(1);
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
