package firm.provider.repository;

import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
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

    public static String SELECT_BY_ID = "SELECT * FROM firms where id=?";
    public static String SELECT_BY_NAME = "SELECT * FROM firms where name=?";
    public static String SELECT = "SELECT * FROM firms";
    public static String INSERT = "INSERT INTO firms(name) VALUES (?)";

    DataSource dataSource;

    public Optional<Firm> findById(long id) {
        Firm firm = selectById(dataSource, id);

        if (firm == null) {
            return Optional.empty();
        }

        List<Product> products = ProductRepository.selectByFirmId(dataSource, id);
        for (Product product : products) {
            product.setOrders(OrderRepository.selectByproductId(dataSource, product.getId()));
        }

        List<Order> orders = OrderRepository.selectByFirmId(dataSource, id);

        for (Order order : orders) {
            order.setProducts(ProductRepository.selectByOrderId(dataSource, order.getId()));
            order.setFirm(firm);
        }

        firm.setProducts(products);
        firm.setOrders(orders);

        return Optional.of(firm);
    }

    public Optional<Firm> findByName(String name) {
        Firm firm = selectByName(dataSource, name);

        if (firm == null) {
            return Optional.empty();
        }

        List<Product> products = ProductRepository.selectByFirmId(dataSource, firm.getId());
        for (Product product : products) {
            product.setOrders(OrderRepository.selectByproductId(dataSource, product.getId()));
        }

        List<Order> orders = OrderRepository.selectByFirmId(dataSource, firm.getId());

        for (Order order : orders) {
            order.setProducts(ProductRepository.selectByOrderId(dataSource, order.getId()));
            order.setFirm(firm);
        }

        firm.setProducts(products);
        firm.setOrders(orders);

        return Optional.of(firm);
    }

    public List<Firm> getAll() {

        List<Firm> firms = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT);

            while (result.next()) {
                firms.add(extractFirm(result));
            }

            for (Firm firm: firms) {
                List<Product> products = ProductRepository.selectByFirmId(dataSource, firm.getId());
                for (Product product : products) {
                    product.setOrders(OrderRepository.selectByproductId(dataSource, product.getId()));
                }

                List<Order> orders = OrderRepository.selectByFirmId(dataSource, firm.getId());

                for (Order order : orders) {
                    order.setProducts(ProductRepository.selectByOrderId(dataSource, order.getId()));
                    order.setFirm(firm);
                }

                firm.setProducts(products);
                firm.setOrders(orders);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return firms;
    }

    public boolean save(Firm firm) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(INSERT);
            statement.setObject(1, firm.getName());

            statement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private static Firm extractFirm(ResultSet resultSet) throws SQLException {


        return new Firm(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("password"),
                null,
                null
        );
    }

    protected static Firm selectById(DataSource dataSource,long id) {

        Firm firm = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                firm = extractFirm(result);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return firm;
    }

    protected static Firm selectByName(DataSource dataSource,String name) {

        Firm firm = null;

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(SELECT_BY_NAME);
            statement.setString(1, name);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                firm = extractFirm(result);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return firm;
    }
}
