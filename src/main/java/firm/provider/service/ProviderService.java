package firm.provider.service;

import firm.provider.model.Provider;

import java.util.List;

public interface ProviderService {

    boolean addProvider(Provider provider);

    List<Provider> getProviders();
}
