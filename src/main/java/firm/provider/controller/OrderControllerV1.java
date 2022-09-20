package firm.provider.controller;

import firm.provider.dto.OrderDto;
import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders/")
@AllArgsConstructor
public class OrderControllerV1 {

    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = new ArrayList<>();

        for (Order order : orderService.getAll()) {
            orders.add(OrderDto.fromOrder(order));
        }

        return ResponseEntity.ok(orders);
    }

    @PostMapping("")
    public ResponseEntity addOrder(@RequestBody Order order) {
        if (orderService.addOrder(order)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
