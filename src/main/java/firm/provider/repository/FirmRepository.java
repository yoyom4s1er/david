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

        final String request = "SELECT * FROM collector_firms where id=?";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(new FirmCollector(
                        result.getLong("id"),
                        result.getString("name"),
                        new ArrayList<>()
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    public List<FirmCollector> getAll() {

        final String request = "SELECT * FROM collector_firms";

        List<FirmCollector> firmCollectors = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(request);

            while (result.next()) {
                firmCollectors.add(new FirmCollector(
                        result.getLong("id"),
                        result.getString("name"),
                        new ArrayList<>()
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return firmCollectors;
    }
}
