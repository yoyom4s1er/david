package firm.provider.service;

import firm.provider.model.Firm;

import java.util.List;
import java.util.Optional;

public interface FirmService {

    public Optional<Firm> getFirm(long id);

    public List<Firm> getFirms();

    public void addFirm(Firm firm);

    public void deleteFirm(Firm firm);

    public boolean updateFirm(Firm firm);
}
