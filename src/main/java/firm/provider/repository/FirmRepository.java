package firm.provider.repository;

import firm.provider.model.FirmCollector;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FirmRepository {

    private final String url;
    private final String username;
    private final String password;

    @Autowired
    public FirmRepository(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String user,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = user;
        this.password = password;
    }

    public Optional<FirmCollector> findById(long id) {

        final String request = "SELECT * FROM collector_firms where id=?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setLong(1, id);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    public List<FirmCollector> getAll() {

        final String request = "SELECT * FROM collector_firms";

        List<FirmCollector> firmCollectors = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

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
