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
        if (order.getOperationType() == OperationType.BUY) {
            return processOperationBuy(order);
        }
        return orderRepository.save(order);
    }

    private boolean processOperationBuy(Order order) {
        Provider provider = providerRepository.findById(order.getOperationTargetId()).get();
        List<Product> requiredProducts = productRepository.getAllByLocationTypeAndLocationId(LocationType.FIRM_PROVIDER, order.getOperationTargetId());

        for (Product product: order.getProducts()) {
            if (!requiredProducts.contains(product)) {
                return false;
            }
        }

        for (Product product: order.getProducts()) {
            product.setLocationType(LocationType.FIRM_COLLECTOR);
            product.setLocation_id(order.getFirm().getId());
        }

        orderRepository.save(order);

        return productRepository.save(order.getProducts());
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
