package firm.provider.service;

import firm.provider.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    boolean addOrder(Order order);

    List<Order> getAllByFirmId(long id);
}