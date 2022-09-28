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
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public List<Product> getAllByLocationType(LocationType locationType) {
        return productRepository.getAllByLocationType(locationType);
    }

    @Override
    public boolean addProduct(Product product) {
        return productRepository.save(product);
    }
}
