package firm.provider.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
import lombok.Data;

import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Data
public class FirmDto {

    private long id;

    private String name;

    @JsonIgnoreProperties(value = { "firm", "products" })
    private List<Order> orders;

    @JsonIgnoreProperties(value = { "orders", "locationType", "locationId" })
    private List<Product> products;

    public Firm toOrder() {
        Firm firm = new Firm();

        firm.setId(id);
        firm.setName(name);
        firm.setOrders(orders);
        firm.setProducts(products);

        return firm;
    }

    public static FirmDto fromFirm(Firm firm) {
        FirmDto firmDto = new FirmDto();

        firmDto.setId(firm.getId());
        firmDto.setName(firm.getName());
        firmDto.setOrders(firm.getOrders());
        firmDto.setProducts(firm.getProducts());

        return firmDto;
    }
}
