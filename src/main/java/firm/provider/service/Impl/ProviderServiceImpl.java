package firm.provider.service.Impl;

import firm.provider.model.Provider;
import firm.provider.repository.DatabaseQueryHandler;
import firm.provider.repository.ProviderRepository;
import firm.provider.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final DatabaseQueryHandler databaseQueryHandler;

    @Override
    public boolean addProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public Provider getProvider(long id) {
        return null;
    }
}
