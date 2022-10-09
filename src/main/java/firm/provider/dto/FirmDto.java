package firm.provider.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FirmDto {

    private long id;

    private String name;

    private List<Long> orders_id;

    @JsonIgnoreProperties(value = { "orders", "locationType", "locationId" })
    private List<Product> products;

    public Firm toOrder() {
        Firm firm = new Firm();

        firm.setId(id);
        firm.setName(name);
        firm.setProducts(products);

        firm.setOrders(orders_id.stream().map(id -> new Order(id)).collect(Collectors.toList()));

        return firm;
    }

    public static FirmDto fromFirm(Firm firm) {
        FirmDto firmDto = new FirmDto();

        firmDto.setId(firm.getId());
        firmDto.setName(firm.getName());
        firmDto.setProducts(firm.getProducts());

        firmDto.setOrders_id(firm.getOrders().stream().map(Order::getId).collect(Collectors.toList()));


        return firmDto;
    }
}
