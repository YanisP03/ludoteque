package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Ajouter un client
    public Client ajouterClient(Client client) {
        return clientRepository.save(client);
    }

    // Trouver clients dont le nom commence par
    public List<Client> trouverClientsParNom(String prefix) {
        return clientRepository.findByNomStartingWithIgnoreCase(prefix);
    }

    // Modification complète client + adresse
    @Transactional
    public Client modifierClient(Integer id, Client clientModifie) throws Exception {

        Client clientBD = clientRepository.findById(id)
                .orElseThrow(() -> new Exception("Client non trouvé"));

        // infos client
        clientBD.setNom(clientModifie.getNom());
        clientBD.setPrenom(clientModifie.getPrenom());
        clientBD.setEmail(clientModifie.getEmail());
        clientBD.setNoTelephone(clientModifie.getNoTelephone());

        // adresse
        Adresse adresseBD = clientBD.getAdresse();
        Adresse adresseNew = clientModifie.getAdresse();

        adresseBD.setRue(adresseNew.getRue());
        adresseBD.setCodePostal(adresseNew.getCodePostal());
        adresseBD.setVille(adresseNew.getVille());

        // Hibernate flush automatique
        return clientBD;
    }

    // Modifier uniquement l'adresse
    @Transactional
    public Client modifierAdresseClient(Integer noClient, Adresse nouvelleAdresse) throws Exception {

        Client client = clientRepository.findById(noClient)
                .orElseThrow(() -> new Exception("Client non trouvé"));

        Adresse adresse = client.getAdresse();
        adresse.setRue(nouvelleAdresse.getRue());
        adresse.setCodePostal(nouvelleAdresse.getCodePostal());
        adresse.setVille(nouvelleAdresse.getVille());

        return client;
    }

    // Suppression client
    @Transactional
    public void supprimerClient(Integer noClient) throws Exception {
        if (!clientRepository.existsById(noClient)) {
            throw new Exception("Client non trouvé");
        }
        clientRepository.deleteById(noClient);
    }
}
