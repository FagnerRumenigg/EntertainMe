package entertain_me.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

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

    @Bean
    public DataSource dataSource() {
        try{
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://" + postgresHost + ":" + postgresPort + "/" + postgresDatabase);
            dataSource.setUsername(postgresUsername);
            dataSource.setPassword(postgresPassword);

            return dataSource;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar no banco");
        }
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}