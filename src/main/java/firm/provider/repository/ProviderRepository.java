package firm.provider.repository;

import firm.provider.model.Firm;
import firm.provider.model.Product;
import firm.provider.model.Provider;
import firm.provider.util.LocationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("DuplicatedCode")
@Component
@AllArgsConstructor
public class ProviderRepository {

    public static String INSERT = "INSERT INTO providers(name) VALUES (?)";
    public static String SELECT_BY_ID = "SELECT * FROM providers where id=?";
    public static String SELECT = "SELECT * FROM providers";

    DataSource dataSource;

    public List<Provider> getAll() {

        List<Provider> providers = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                providers.add(extractProvider(result));
            }

            for (Provider provider : providers) {
                provider.setProducts(ProductRepository.selectByProviderId(dataSource, provider.getId()));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return providers;
    }

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

    private static Provider extractProvider(ResultSet result) throws SQLException {
        return new Provider(
                result.getLong("id"),
                result.getString("name"),
                null
        );
    }

    protected static Provider selectById(DataSource dataSource, long id) {

        Provider provider = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                provider = extractProvider(result);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return provider;
    }

}
