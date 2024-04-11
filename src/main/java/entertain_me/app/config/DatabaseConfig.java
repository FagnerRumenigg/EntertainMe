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
    
    @Value("${DATABASE_URL}")
    private String urlHeroku;


    @Bean
    public DataSource dataSource() {
    	System.out.println("Chegou2");
    	System.out.println(postgresHost);
    	System.out.println(postgresPort);
    	System.out.println(postgresUsername);
    	System.out.println(postgresPassword);
    	System.out.println(postgresDatabase);
    	
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + postgresHost + ":" + postgresPort + "/" + postgresDatabase);
        dataSource.setUsername(postgresUsername);
        dataSource.setPassword(postgresPassword);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
