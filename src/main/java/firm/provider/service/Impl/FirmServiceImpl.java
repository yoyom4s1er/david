package firm.provider.service.Impl;

import firm.provider.model.Firm;
import firm.provider.repository.FirmRepository;
import firm.provider.service.FirmService;
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
    private final ProductService productService;



    @Override
    public Optional<Firm> findById(long id) {
        Optional<Firm> firmCollector = firmRepository.findById(id);
        if (firmCollector.isEmpty()) {
            return Optional.empty();
        }
        firmCollector.get().setProducts(
                productService.getAllByLocationTypeAndLocationId(LocationType.FIRM_COLLECTOR, firmCollector.get().getId()));

        return firmCollector;
    }

    @Override
    public Optional<Firm> findById(Firm firm) {
        Optional<Firm> firmCollector = firmRepository.findById(firm.getId());
        if (firmCollector.isEmpty()) {
            return Optional.empty();
        }

        firmCollector.get().setProducts(
                productService.getAllByLocationTypeAndLocationId(LocationType.FIRM_COLLECTOR, firmCollector.get().getId()));

        return firmCollector;
    }

    @Override
    public List<Firm> getAll() {
        List<Firm> firms = firmRepository.getAll();
        for (Firm firm: firms) {
            firm.setProducts(productService.getAllByLocationTypeAndLocationId(LocationType.FIRM_COLLECTOR, firm.getId()));
        }

        return firms;
    }

    @Override
    public boolean addFirm(Firm firm) {
        return firmRepository.save(firm);
    }

    /*@Override
    public void deleteFirm(FirmCollector firmCollector) {
        firmRepository.delete(firmCollector);
    }

    @Override
    public boolean updateFirm(FirmCollector firmCollector) {
        Optional<FirmCollector> firmFromStorage = firmRepository.findById(firmCollector.getId());

        if (firmFromStorage.isEmpty()) {
            return false;
        }

        firmFromStorage.get().setFirmType(firmCollector.getFirmType());
        firmFromStorage.get().setName(firmCollector.getName());
        firmFromStorage.get().setProducts(firmCollector.getProducts());

        firmRepository.save(firmFromStorage.get());

        return true;
    }*/
}
