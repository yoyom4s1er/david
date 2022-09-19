package firm.provider.controller;

import firm.provider.model.Firm;
import firm.provider.model.Provider;
import firm.provider.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/providers/")
@AllArgsConstructor
public class ProviderControllerV1 {

    private final ProviderService providerService;

    /*@GetMapping("{id}")
    public ResponseEntity<Firm> getProvider(@PathVariable long id) {
        Optional<Firm> firm = providerService.findById(id);

        if (firm.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(firm.get());
    }

    @GetMapping("")
    public ResponseEntity<List<Firm>> getFirm() {
        List<Firm> firms = providerService.getAll();

        return ResponseEntity.ok(firms);
    }*/

    @PostMapping("")
    public ResponseEntity addFirmCollector(@RequestBody Provider provider) {
        if (providerService.addProvider(provider)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
