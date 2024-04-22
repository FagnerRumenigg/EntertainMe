package entertain_me.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


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
    
    @Value("${postgres.DATABASE_URL}")
    private String urlHeroku;


    @Bean
    DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + postgresHost + ":" + postgresPort + "/" + postgresDatabase);
        dataSource.setUsername(postgresUsername);
        dataSource.setPassword(postgresPassword);
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
