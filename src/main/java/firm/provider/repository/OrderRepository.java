package firm.provider.repository;

import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
import firm.provider.util.OperationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
@Component
@AllArgsConstructor
public class OrderRepository {

    DataSource dataSource;

    public List<Order> getAll() {

        final String request = "SELECT * FROM orders";

        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(request);

            while (result.next()) {
                orders.add(new Order(
                        result.getLong("id"),
                        new Firm(result.getLong("firm_id")),
                        OperationType.valueOf(result.getString("operation_type")),
                        result.getLong("operation_target_id"),
                        LocalDateTime.ofInstant(result.getTimestamp("date").toInstant(), ZoneId.systemDefault()),
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orders;
    }

    public boolean save(Order order) {
        final String request = "INSERT INTO orders(operation_target_id, date, operation_type, firm_id) VALUES (?,?,?,?)";
        final String manyToManyRequest = "INSERT INTO orders_products(order_id, products_id) VALUES (?,?)";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setObject(1, order.getOperationTargetId());
            statement.setObject(2, order.getDate());
            statement.setString(3, order.getOperationType().name());
            statement.setObject(4, order.getFirm().getId());

            statement.executeUpdate();

            statement = conn.prepareStatement(manyToManyRequest);

            for (Product product : order.getProducts()) {
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.addBatch();
            }

            statement.executeBatch();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean save(List<Order> orders) {
        final String request = "INSERT INTO orders(operation_target_id, date, operation_type, firm_id) VALUES (?,?,?,?)";
        final String manyToManyRequest = "INSERT INTO orders_products(order_id, products_id) VALUES (?,?)";

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);

            for (Order order: orders) {
                statement.setObject(1, order.getOperationTargetId());
                statement.setObject(2, order.getDate());
                statement.setString(3, order.getOperationType().name());
                statement.setObject(4, order.getFirm().getId());
                statement.addBatch();
            }

            statement.executeBatch();

            statement = conn.prepareStatement(manyToManyRequest);

            for (Order order: orders) {
                for (Product product : order.getProducts()) {
                    statement.setLong(1, order.getId());
                    statement.setLong(2, product.getId());
                    statement.addBatch();
                }
            }

            statement.executeBatch();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<Order> getAllByFirmId(long id) {
        final String request = "SELECT * FROM orders where firm_id=?";

        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                orders.add(new Order(
                        result.getLong("id"),
                        new Firm(result.getLong("firm_id")),
                        OperationType.valueOf(result.getString("operation_type")),
                        result.getLong("operation_target_id"),
                        LocalDateTime.ofInstant(result.getTimestamp("date").toInstant(), ZoneId.systemDefault()),
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orders;
    }
}
