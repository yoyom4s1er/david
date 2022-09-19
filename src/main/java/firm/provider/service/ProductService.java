package firm.provider.service;

import firm.provider.model.Product;
import firm.provider.util.LocationType;

import java.util.List;

public interface ProductService {

    List<Product> getAllByLocationTypeAndLocationId(LocationType locationType, long locationId);

    boolean addProduct(Product product);
}
