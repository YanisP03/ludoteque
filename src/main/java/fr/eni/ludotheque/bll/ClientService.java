package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // S2008 : ajouter un client
    public Client ajouterClient(Client client) {
        return clientRepository.save(client);
    }

    // S2009 : trouver clients dont le nom commence par prefix
    public List<Client> trouverClientsParNom(String prefix) {
        return clientRepository.findByNomStartingWithIgnoreCase(prefix);
    }

    // S2010 : modification complète d'un client
    public Client modifierClient(Client client) throws Exception {
        Optional<Client> clientBD = clientRepository.findById(client.getNoClient());
        if (clientBD.isEmpty()) {
            throw new Exception("Client non trouvé");
        }
        return clientRepository.save(client);
    }

    // S2011 : modifier uniquement l'adresse d'un client
    public Client modifierAdresseClient(Integer noClient, Adresse nouvelleAdresse) throws Exception {
        Client client = clientRepository.findById(noClient)
                .orElseThrow(() -> new Exception("Client non trouvé"));

        client.setAdresse(nouvelleAdresse);
        return clientRepository.save(client);
    }

    // S3020 - suppression client
    @Transactional
    public void supprimerClient(Integer noClient) throws Exception {
        if (!clientRepository.existsById(noClient)) {
            throw new Exception("Client non trouvé");
        }
        clientRepository.deleteById(noClient);
    }
}
