package firm.provider.repository;

import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.util.OperationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
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
                        null,
                        OperationType.valueOf(result.getString("operation_type")),
                        result.getLong("operation_target_id"),
                        LocalDateTime.parse(result.getString("date")),
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

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(request);
            statement.setObject(1, order.getOperationTargetId());
            statement.setObject(2, order.getDate());
            statement.setObject(3, order.getOperationType());
            statement.setObject(4, order.getFirm().getId());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
