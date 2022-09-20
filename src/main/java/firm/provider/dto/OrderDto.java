package firm.provider.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
import firm.provider.util.OperationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private long id;

    @JsonIgnoreProperties(value = { "orders", "products" })
    private Firm firm;

    private OperationType operationType;

    private long operationTargetId;

    private LocalDateTime date;

    private List<Product> products;

    public Order toOrder() {
        Order order = new Order();
        order.setId(id);
        order.setFirm(firm);
        order.setOperationType(operationType);
        order.setOperationTargetId(operationTargetId);
        order.setDate(date);
        order.setProducts(products);

        return order;
    }

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(order.getId());
        orderDto.setFirm(order.getFirm());
        orderDto.setOperationType(order.getOperationType());
        orderDto.setOperationTargetId(order.getOperationTargetId());
        orderDto.setDate(order.getDate());
        orderDto.setProducts(order.getProducts());

        return orderDto;
    }
}
