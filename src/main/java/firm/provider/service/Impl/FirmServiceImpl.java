package firm.provider.service.Impl;

import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.repository.FirmRepository;
import firm.provider.repository.OrderRepository;
import firm.provider.repository.ProductRepository;
import firm.provider.service.FirmService;
import firm.provider.service.OrderService;
import firm.provider.service.ProductService;
import firm.provider.util.LocationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public Optional<Firm> findById(long id) {
        return firmRepository.findById(id);
    }

    @Override
    public Optional<Firm> findById(Firm firm) {
        return firmRepository.findById(firm.getId());
    }

    @Override
    public List<Firm> getAll() {
        return firmRepository.getAll();
    }

    @Override
    public boolean addFirm(Firm firm) {
        return firmRepository.save(firm);
    }

    @Override
    public void deleteFirm(Firm firmCollector) {
        /*firmRepository.delete(firmCollector);*/
    }

    @Override
    public boolean updateFirm(Firm firmCollector) {
        Optional<Firm> firmFromStorage = firmRepository.findById(firmCollector.getId());

        if (firmFromStorage.isEmpty()) {
            return false;
        }

        /*firmFromStorage.get().setFirmType(firmCollector.getFirmType());*/
        firmFromStorage.get().setName(firmCollector.getName());
        firmFromStorage.get().setProducts(firmCollector.getProducts());

        firmRepository.save(firmFromStorage.get());

        return true;
    }
}
