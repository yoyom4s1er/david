package firm.provider.service.Impl;

import firm.provider.model.Firm;
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

    @Override
    public Optional<Firm> getFirm(long id) {
        return firmRepository.findById(id);
    }

    @Override
    public List<Firm> getFirms() {
        return firmRepository.findAll();
    }

    @Override
    public void addFirm(Firm firm) {
        firmRepository.save(firm);
    }

    @Override
    public void deleteFirm(Firm firm) {
        firmRepository.delete(firm);
    }

    @Override
    public boolean updateFirm(Firm firm) {
        Optional<Firm> firmFromStorage = firmRepository.findById(firm.getId());

        if (firmFromStorage.isEmpty()) {
            return false;
        }

        firmFromStorage.get().setFirmType(firm.getFirmType());
        firmFromStorage.get().setName(firm.getName());
        firmFromStorage.get().setProducts(firm.getProducts());

        firmRepository.save(firmFromStorage.get());

        return true;
    }
}
