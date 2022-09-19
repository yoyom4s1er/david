package firm.provider.repository;

import firm.provider.model.Firm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FirmRepository {

    DataSource dataSource;

    public Optional<Firm> findById(long id) {

        final String request = "SELECT * FROM firms where id=?";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new Firm(
                        result.getLong("id"),
                        result.getString("name"),
                        null,
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Firm> getAll() {

        final String request = "SELECT * FROM firms";

        List<Firm> firms = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(request);

            while (result.next()) {
                firms.add(new Firm(
                        result.getLong("id"),
                        result.getString("name"),
                        null,
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return firms;
    }

    public boolean save(Firm firm) {


        final String request = "INSERT INTO firms(name) VALUES (?)";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setObject(1, firm.getName());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
