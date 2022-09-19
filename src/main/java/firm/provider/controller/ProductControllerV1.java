package firm.provider.controller;

import firm.provider.model.Product;
import firm.provider.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products/")
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
}
