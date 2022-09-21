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

    public static String INSERT = "INSERT INTO providers(name) VALUES (?)";
    public static String SELECT_BY_ID = "SELECT * FROM providers where id=?";

    DataSource dataSource;

    public boolean save(Provider provider) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT);
            statement.setObject(1, provider.getName());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Optional<Provider> findById(long id) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID);
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
