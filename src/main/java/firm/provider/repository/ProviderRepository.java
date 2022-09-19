package firm.provider.repository;

import firm.provider.model.Provider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("DuplicatedCode")
@Component
@AllArgsConstructor
public class ProviderRepository {

    DataSource dataSource;

    public boolean save(Provider provider) {


        final String request = "INSERT INTO providers(name) VALUES (?)";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setObject(1, provider.getName());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
