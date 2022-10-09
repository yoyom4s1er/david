package firm.provider.service.Impl;

import firm.provider.dto.BuyProduct;
import firm.provider.model.Order;
import firm.provider.model.Product;
import firm.provider.repository.FirmRepository;
import firm.provider.repository.OrderRepository;
import firm.provider.repository.ProductRepository;
import firm.provider.repository.ProviderRepository;
import firm.provider.service.OrderService;
import firm.provider.utils.LocationType;
import firm.provider.utils.OperationType;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        try {

            order.setFirm(firmRepository.findByName(order.getFirm().getName()).get());
            order.setDate(LocalDateTime.now());

            if (order.getOperationType() == OperationType.BUY) {
                for (Product product : order.getProducts()) {
                    product.setLocationType(LocationType.FIRM_COLLECTOR);
                    product.setLocation_id(order.getFirm().getId());
                    productRepository.updateLocationTypeAndLocationIdById(product);
                }
            }
            else if (order.getOperationType() == OperationType.SELL) {
                for (Product product : order.getProducts()) {
                    product.setLocationType(LocationType.CLIENT);
                    product.setLocation_id(order.getOperationTargetId());
                    productRepository.updateLocationTypeAndLocationIdById(product);
                }
            }
            if (!orderRepository.save(order)) {
                throw new RuntimeException("Database Exception");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
