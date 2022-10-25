package firm.provider.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@AllArgsConstructor
public class ClientRepository {

    DataSource dataSource;
}
