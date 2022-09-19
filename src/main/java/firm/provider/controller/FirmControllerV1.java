package firm.provider.controller;

import firm.provider.model.Firm;
import firm.provider.service.FirmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/firm/")
@AllArgsConstructor
public class FirmControllerV1 {

    private final FirmService firmService;

    @GetMapping(name = "{id}")
    public ResponseEntity<Firm> getFirm(@PathVariable long id) {
        Optional<Firm> firm = firmService.getFirm(id);

        if (firm.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(firm.get());
    }
}
