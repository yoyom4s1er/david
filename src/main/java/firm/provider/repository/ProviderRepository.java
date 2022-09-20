package firm.provider.repository;

import firm.provider.model.Firm;
import firm.provider.model.Provider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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

    public Optional<Provider> findById(long id) {

        final String request = "SELECT * FROM providers where id=?";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new Provider(
                        result.getLong("id"),
                        result.getString("name"),
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }
}
