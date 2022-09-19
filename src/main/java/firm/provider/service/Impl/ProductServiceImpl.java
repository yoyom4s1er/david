package firm.provider.service.Impl;

import firm.provider.model.Product;
import firm.provider.repository.ProductRepository;
import firm.provider.service.ProductService;
import firm.provider.util.LocationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllByLocationTypeAndLocationId(LocationType locationType, long locationId) {

        return productRepository.getAllByLocationTypeAndLocationId(locationType, locationId);
    }
}
