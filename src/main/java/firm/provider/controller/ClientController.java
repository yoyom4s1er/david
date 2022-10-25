package firm.provider.controller;

import firm.provider.model.Client;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/clients")
@AllArgsConstructor
public class ClientController {

    @GetMapping("")
    public ResponseEntity<Client> getClients() {
        return
    }
}
