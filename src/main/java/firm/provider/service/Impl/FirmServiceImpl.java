package firm.provider.service.Impl;

import firm.provider.model.FirmCollector;
import firm.provider.repository.FirmRepository;
import firm.provider.service.FirmService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;

    /*@Override
    public Optional<FirmCollector> getFirm(long id) {
        return firmRepository.findById(id);
    }*/

    @Override
    public List<FirmCollector> getAll() {
        return firmRepository.getAll();
    }

    /*@Override
    public void addFirm(FirmCollector firmCollector) {
        firmRepository.save(firmCollector);
    }

    @Override
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
