package firm.provider.service.Impl;

import firm.provider.model.Provider;
import firm.provider.repository.ProviderRepository;
import firm.provider.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public boolean addProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public List<Provider> getProviders() {
        return providerRepository.getAll();
    }

    public Provider getProvider(long id) {
        return null;
    }
}
