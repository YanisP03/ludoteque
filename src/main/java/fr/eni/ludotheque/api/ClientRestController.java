package fr.eni.ludotheque.api;

import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    // S2008 / S3019 - ajouter un client
    @PostMapping
    public ResponseEntity<Client> ajouterClient(@RequestBody ClientDTO dto) {
        Adresse adresse = new Adresse(
                dto.getRue(),
                dto.getCodePostal(),
                dto.getVille()
        );

        Client client = new Client(
                dto.getNom(),
                dto.getPrenom(),
                dto.getEmail(),
                adresse
        );
        client.setNoTelephone(dto.getNoTelephone());

        Client clientCree = clientService.ajouterClient(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientCree);
    }

    // S2010 / S3021 - modifier complètement un client (infos + adresse)
    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(
            @PathVariable Integer id,
            @RequestBody ClientDTO dto
    ) {
        try {
            Adresse adresse = new Adresse(
                    dto.getRue(),
                    dto.getCodePostal(),
                    dto.getVille()
            );

            Client clientModifie = new Client(
                    dto.getNom(),
                    dto.getPrenom(),
                    dto.getEmail(),
                    adresse
            );
            clientModifie.setNoTelephone(dto.getNoTelephone());

            Client clientMAJ = clientService.modifierClient(id, clientModifie);
            return ResponseEntity.ok(clientMAJ);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // S3020 - supprimer un client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable Integer id) {
        try {
            clientService.supprimerClient(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // S3022 - modifier uniquement l'adresse du client
    @PatchMapping("/{id}/adresse")
    public ResponseEntity<Client> modifierAdresseClient(
            @PathVariable Integer id,
            @RequestBody ClientDTO dto
    ) {
        try {
            // On crée une nouvelle Adresse à partir du DTO
            Adresse nouvelleAdresse = new Adresse(
                    dto.getRue(),
                    dto.getCodePostal(),
                    dto.getVille()
            );

            // Appelle pour modifier uniquement l'adresse
            Client clientMAJ = clientService.modifierAdresseClient(id, nouvelleAdresse);

            return ResponseEntity.ok(clientMAJ);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // S3023 - trouver les clients dont le nom commence par la chaîne fournie
    @GetMapping("/search")
    public ResponseEntity<List<Client>> chercherClientsParNom(@RequestParam String prefix) {

        List<Client> clients = clientService.trouverClientsParNom(prefix);

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

}
