package firm.provider.repository;

import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
import firm.provider.utils.OperationType;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    public static String SELECT = "SELECT * FROM orders";
    public static String INSERT = "INSERT INTO orders(operation_target_id, date, operation_type, total_price, firm_id) VALUES (?,?,?,?,?)";
    public static String INSERT_INTO_ORDERS_PRODUCTS = "INSERT INTO orders_products(order_id, products_id) VALUES (?,?)";
    public static String SELECT_BY_FIRM_ID = "SELECT * FROM orders where firm_id=?";
    public static String SELECT_BY_PRODUCT_ID = "select order_id from orders_products where products_id=?";
    public static String SELECT_BY_ID = "Select * from orders where id=?";

    DataSource dataSource;

    public List<Order> getAll() {

        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT);

            while (result.next()) {
                orders.add(new Order(
                        result.getLong("id"),
                        new Firm(result.getLong("firm_id")),
                        OperationType.valueOf(result.getString("operation_type")),
                        result.getLong("operation_target_id"),
                        null,
                        LocalDateTime.ofInstant(result.getTimestamp("date").toInstant(), ZoneId.systemDefault()),
                        result.getFloat("total_price"),
                        null
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orders;
    }

    public boolean save(Order order) {

        long id = insert(dataSource, order);
        order.setId(id);

        if (id == 0) {
            return false;
        }

        if (!insertIntoOrdersProducts(dataSource, order)) {
            return false;
        }

        return true;
    }

    public boolean save(List<Order> orders) {

        /*if (!insert(dataSource, orders)) {
            return false;
        }
        if (!insertIntoOrdersProducts(dataSource, orders)) {
            return false;
        }*/

        return true;
    }

    public List<Order> getAllByFirmId(long id) {
        List<Order> orders = selectByFirmId(dataSource, id);
        Firm firm = FirmRepository.selectById(dataSource, id);
        firm.setOrders(orders);
        firm.setProducts(ProductRepository.selectByFirmId(dataSource, id));

        for (Order order:
             orders) {
            order.setFirm(firm);
            order.setProducts(ProductRepository.selectByOrderId(dataSource, order.getId()));
            if (order.getOperationType() == OperationType.BUY) {
                order.setLocationTargetName(
                        ProviderRepository.selectById(dataSource, order.getOperationTargetId()).getName()
                );
            }
            /*else if (order.getOperationType() == OperationType.SELL) {
            }*/

        }

        return orders;
    }

    protected static List<Order> selectByFirmId(DataSource dataSource, long id) {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_FIRM_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                orders.add(extractOrder(result));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orders;
    }

    protected static Order selectById(DataSource dataSource, long id) {
        Order order = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                order = extractOrder(result);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return order;
    }

    protected static Long insert(DataSource dataSource, List<Order> orders) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            for (Order order: orders) {
                statement.setObject(1, order.getOperationTargetId());
                statement.setObject(2, order.getDate());
                statement.setString(3, order.getOperationType().name());
                statement.setFloat(4, order.getTotalPrice());
                statement.setObject(5, order.getFirm().getId());
                statement.addBatch();
            }

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return (long)0;
    }

    protected static Long insert(DataSource dataSource, Order order) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            statement.setObject(1, order.getOperationTargetId());
            statement.setObject(2, order.getDate());
            statement.setString(3, order.getOperationType().name());
            statement.setFloat(4, order.getTotalPrice());
            statement.setObject(5, order.getFirm().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return (long)0;

    }

    protected static boolean insertIntoOrdersProducts(DataSource dataSource, List<Order> orders) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT_INTO_ORDERS_PRODUCTS);

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

    protected static boolean insertIntoOrdersProducts(DataSource dataSource, Order order) {

        System.out.println(order);

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT_INTO_ORDERS_PRODUCTS);

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

    protected static List<Order> selectByproductId(DataSource dataSource, long id) {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_PRODUCT_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                long orderId = result.getLong("order_id");
                orders.add(selectById(dataSource, orderId));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orders;
    }

    private static Order extractOrder(ResultSet result) throws SQLException {
        return new Order(
                result.getLong("id"),
                null,
                OperationType.valueOf(result.getString("operation_type")),
                result.getLong("operation_target_id"),
                null,
                LocalDateTime.ofInstant(result.getTimestamp("date").toInstant(), ZoneId.systemDefault()),
                result.getFloat("total_price"),
                null
        );
    }
}
