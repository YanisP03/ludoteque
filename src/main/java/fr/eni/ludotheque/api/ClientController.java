/*
package fr.eni.ludotheque.api;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> ajouterClient(@RequestBody Client client) {
        Client clientAjoute = clientService.ajouterClient(client);
        return ResponseEntity.ok(clientAjoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable Integer id) {
        try {
            clientService.supprimerClient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(
            @PathVariable Integer id,
            @RequestBody Client clientModifie
    ) {
        try {
            Client clientMAJ = clientService.modifierClient(id, clientModifie);
            return ResponseEntity.ok(clientMAJ);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
*/
