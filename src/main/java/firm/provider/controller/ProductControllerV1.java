package firm.provider.controller;

import firm.provider.model.Product;
import firm.provider.service.ProductService;
import firm.provider.util.LocationType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductControllerV1 {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity addFirmCollector(@RequestBody Product product) {
        if (productService.addProduct(product)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = "", params = "locationType")
    public ResponseEntity<List<Product>> getAllByLocationType(@RequestParam(name = "locationType") String locationType) {

        if (locationType.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        LocationType locType = null;

        try {
            locType = LocationType.valueOf(locationType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(productService.getAllByLocationType(locType));
    }
}
