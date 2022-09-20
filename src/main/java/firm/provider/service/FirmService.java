package firm.provider.service;

import firm.provider.model.Firm;

import java.util.List;
import java.util.Optional;

public interface FirmService {

    public Optional<Firm> findById(long id);

    public Optional<Firm> findById(Firm firm);

    public List<Firm> getAll();

    public boolean addFirm(Firm firm);

    public void deleteFirm(Firm firm);

    public boolean updateFirm(Firm firm);
}
