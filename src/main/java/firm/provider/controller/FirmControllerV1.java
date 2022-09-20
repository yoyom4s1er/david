package firm.provider.controller;

import firm.provider.dto.FirmDto;
import firm.provider.model.Firm;
import firm.provider.service.FirmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<FirmDto>> getFirms() {
        List<FirmDto> firms = new ArrayList<>();

        for (Firm firm : firmService.getAll()) {
            firms.add(FirmDto.fromFirm(firm));
        }

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
