package firm.provider.service;

import firm.provider.model.Product;
import firm.provider.utils.LocationType;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    List<Product> getAllByLocationType(LocationType locationType);

    boolean addProduct(Product product);

    List<Product> getAllByLocationName(String firm, LocationType locType);
}
