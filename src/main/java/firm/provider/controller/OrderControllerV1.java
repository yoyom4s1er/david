package firm.provider.controller;

import firm.provider.dto.OrderDto;
import firm.provider.model.Firm;
import firm.provider.model.Order;
import firm.provider.model.Product;
import firm.provider.service.FirmService;
import firm.provider.service.OrderService;
import firm.provider.service.ProviderService;
import firm.provider.utils.OperationType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/orders")
@AllArgsConstructor
public class OrderControllerV1 {

    private final OrderService orderService;
    private final FirmService firmService;
    private final ProviderService providerService;

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = new ArrayList<>();

        for (Order order : orderService.getAll()) {
            orders.add(OrderDto.fromOrder(order));
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{firmName}")
    public ResponseEntity<List<OrderDto>> getOrdersByFirmName(@PathVariable String firmName) {
        List<OrderDto> orders = new ArrayList<>();

        Firm firm = firmService.findByName(firmName).orElseThrow(
                () -> new RuntimeException("Firm not found")
        );

        for (Order order : orderService.getAllByFirmId(firm.getId())) {
            orders.add(OrderDto.fromOrder(order));
        }

        return ResponseEntity.ok(orders);
    }


    @PostMapping("")
    public ResponseEntity addOrder(@RequestBody OrderDto orderDto) {

        Order order = orderDto.toOrder();

        if (orderService.addOrder(order)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
