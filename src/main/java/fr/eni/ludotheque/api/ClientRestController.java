package fr.eni.ludotheque.api;

import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    // S3019 - ajouter un client
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
}
