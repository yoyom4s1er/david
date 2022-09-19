package firm.provider.repository;

import firm.provider.model.Product;
import firm.provider.util.LocationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
@Component
@AllArgsConstructor
public class ProductRepository {

    DataSource dataSource;

    public List<Product> getAllByLocationTypeAndLocationId(LocationType locationType, long locationId) {


        final String request = "SELECT * FROM products where location_type=? and location_id=?";

        List<Product> products = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setString(1, locationType.name());
            statement.setLong(2, locationId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                products.add(new Product(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("producer"),
                        result.getFloat("price"),
                        null,
                        LocationType.valueOf(result.getString("location_type")),
                        result.getLong("location_id")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return products;
    }

    public boolean save(Product product) {

        final String request = "INSERT INTO products(location_type, location_id, name, price, producer) VALUES (?,?,?,?,?)";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setString(1, product.getLocationType().name());
            statement.setLong(2, product.getLocation_id());
            statement.setString(3, product.getName());
            statement.setFloat(4, product.getPrice());
            statement.setString(5, product.getProducer());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
