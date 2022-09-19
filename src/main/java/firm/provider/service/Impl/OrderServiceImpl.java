package firm.provider.service.Impl;

import firm.provider.model.Order;
import firm.provider.repository.OrderRepository;
import firm.provider.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    @Override
    public boolean addOrder(Order order) {
        return orderRepository.save(order);
    }
}
