package firm.provider.controller;

import firm.provider.model.FirmCollector;
import firm.provider.service.FirmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/firms/")
@AllArgsConstructor
public class FirmControllerV1 {

    private final FirmService firmService;

    @GetMapping("{id}")
    public ResponseEntity<FirmCollector> getFirm(@PathVariable long id) {
        Optional<FirmCollector> firm = firmService.findById(id);

        if (firm.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(firm.get());
    }

    @GetMapping("")
    public ResponseEntity<List<FirmCollector>> getFirm() {
        List<FirmCollector> firmCollectors = firmService.getAll();

        return ResponseEntity.ok(firmCollectors);
    }

    @PostMapping("")
    public ResponseEntity addFirmCollector(@RequestBody FirmCollector firmCollector) {
        if (firmService.addFirm(firmCollector)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
