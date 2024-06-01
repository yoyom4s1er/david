package firm.provider.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseConfig {

    /*private final String url;
    private final String username;
    private final String password;

    public DatabaseConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = user;
        this.password = password;
    }*/

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://localhost:7777/education");
        source.setUsername("postgres");
        source.setPassword("12345678");
        return source;
    }
}
