package firm.provider.service;

import firm.provider.model.Product;
import firm.provider.util.LocationType;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    List<Product> getAllByLocationType(LocationType locationType);

    boolean addProduct(Product product);
}
