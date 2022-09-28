package firm.provider.service.Impl;

import firm.provider.model.Order;
import firm.provider.model.Product;
import firm.provider.model.Provider;
import firm.provider.repository.FirmRepository;
import firm.provider.repository.OrderRepository;
import firm.provider.repository.ProductRepository;
import firm.provider.repository.ProviderRepository;
import firm.provider.service.OrderService;
import firm.provider.util.LocationType;
import firm.provider.util.OperationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FirmRepository firmRepository;
    private final ProviderRepository providerRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Order> getAll() {
        List<Order> orders = orderRepository.getAll();
        for (Order order : orders) {
            order.setFirm(firmRepository.findById(order.getFirm().getId()).get());
        }

        return orderRepository.getAll();
    }

    @Override
    public boolean addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllByFirmId(long id) {
        List<Order> orders = orderRepository.getAllByFirmId(id);
        for (Order order : orders) {
            order.setFirm(firmRepository.findById(order.getFirm().getId()).get());
        }

        return orderRepository.getAll();
    }
}
