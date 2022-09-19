package firm.provider.controller;

import firm.provider.model.Firm;
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
    public ResponseEntity<Firm> getFirm(@PathVariable long id) {
        Optional<Firm> firm = firmService.findById(id);

        if (firm.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(firm.get());
    }

    @GetMapping("")
    public ResponseEntity<List<Firm>> getFirms() {
        List<Firm> firms = firmService.getAll();

        return ResponseEntity.ok(firms);
    }

    @PostMapping("")
    public ResponseEntity addFirmCollector(@RequestBody Firm firm) {
        if (firmService.addFirm(firm)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
