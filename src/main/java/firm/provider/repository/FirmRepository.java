package firm.provider.repository;

import firm.provider.model.FirmCollector;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public Optional<FirmCollector> findById(long id) {

        final String request = "SELECT * FROM collectors where id=?";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new FirmCollector(
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

    public List<FirmCollector> getAll() {

        final String request = "SELECT * FROM collectors";

        List<FirmCollector> firmCollectors = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(request);

            while (result.next()) {
                firmCollectors.add(new FirmCollector(
                        result.getLong("id"),
                        result.getString("name"),
                        null,
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return firmCollectors;
    }

    public boolean save(FirmCollector firmCollector) {


        final String request = "INSERT INTO collectors(name) VALUES (?)";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setObject(1, firmCollector.getName());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
