package firm.provider.service;

import firm.provider.model.FirmCollector;

import java.util.List;
import java.util.Optional;

public interface FirmService {

    public Optional<FirmCollector> findById(long id);

    public List<FirmCollector> getAll();

    /*public void addFirm(FirmCollector firmCollector);

    public void deleteFirm(FirmCollector firmCollector);

    public boolean updateFirm(FirmCollector firmCollector);*/
}
