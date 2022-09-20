package firm.provider.service.Impl;

import firm.provider.model.Order;
import firm.provider.repository.OrderRepository;
import firm.provider.service.FirmService;
import firm.provider.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FirmService firmService;

    @Override
    public List<Order> getAll() {
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders) {
            order.setFirm(firmService.findById(order.getFirm()).get());
        }

        return orderRepository.getAll();
    }

    @Override
    public boolean addOrder(Order order) {
        return orderRepository.save(order);
    }
}
