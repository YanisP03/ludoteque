package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    // S2008 - Création client
    @Test
    @DisplayName("S2008 - ajout client avec adresse - cas positif")
    @Transactional
    public void testAjoutClient() {
        Adresse adresse = new Adresse("rue des Cormorans", "44800", "Saint Herblain");
        Client client = new Client("Stiller", "Ben", "ben.stiller@eni.fr", adresse);
        client.setNoTelephone("0101010101");

        Client clientBD = clientService.ajouterClient(client);

        assertThat(clientBD).isNotNull();
        assertThat(clientBD.getNoClient()).isNotNull();
        assertThat(clientBD.getAdresse()).isNotNull();
    }

    // S2009 - Recherche par nom
    @Test
    @DisplayName("S2009 - trouver clients dont le nom commence par 'St'")
    @Transactional
    public void testTrouverClientsParNom() {

        Adresse a1 = new Adresse("1 rue A", "44000", "Nantes");
        Client c1 = new Client("Stark", "Tony", "tony@stark.com", a1);
        c1.setNoTelephone("0101010101");

        Adresse a2 = new Adresse("2 rue B", "44100", "Nantes");
        Client c2 = new Client("Stuart", "Bob", "bob@stuart.com", a2);
        c2.setNoTelephone("0202020202");

        Adresse a3 = new Adresse("3 rue C", "44200", "Nantes");
        Client c3 = new Client("Dupont", "Jean", "jean@dupont.com", a3);
        c3.setNoTelephone("0303030303");

        clientService.ajouterClient(c1);
        clientService.ajouterClient(c2);
        clientService.ajouterClient(c3);

        List<Client> resultats = clientService.trouverClientsParNom("St");

        assertThat(resultats).hasSize(2);
        assertThat(resultats)
                .extracting(Client::getNom)
                .containsExactlyInAnyOrder("Stark", "Stuart");
    }

    // S2010 - Modification complète OK
    @Test
    @DisplayName("S2010 - modification complète - cas positif")
    @Transactional
    public void testModificationCompleteClient() throws Exception {

        Adresse adresse = new Adresse("rue A", "44000", "Nantes");
        Client client = new Client("Dupont", "Alice", "alice@eni.fr", adresse);
        client.setNoTelephone("0404040404");

        client = clientService.ajouterClient(client);

        client.setNom("DupontMod");
        client.setPrenom("AliceMod");
        client.getAdresse().setRue("rue B");
        client.getAdresse().setVille("Rezé");

        Client modifie = clientService.modifierClient(client);

        assertThat(modifie.getNom()).isEqualTo("DupontMod");
        assertThat(modifie.getPrenom()).isEqualTo("AliceMod");
        assertThat(modifie.getAdresse().getVille()).isEqualTo("Rezé");
    }

    // S2010 - Client non trouvé
    @Test
    @DisplayName("S2010 - modification complète - client non trouvé")
    @Transactional
    public void testModificationClientNonTrouve() {

        Client client = new Client(
                "Inconnu", "John", "john@eni.fr",
                new Adresse("rue X", "44000", "Nantes")
        );
        client.setNoTelephone("0606060606");
        client.setNoClient(999);

        assertThatThrownBy(() -> clientService.modifierClient(client))
                .isInstanceOf(Exception.class)
                .hasMessage("Client non trouvé");
    }

    // S2011 - Modification adresse seule
    @Test
    @DisplayName("S2011 - modifier seulement l'adresse d'un client")
    @Transactional
    public void testModificationAdresseClient() throws Exception {

        Client client = new Client(
                "Dupont", "Alice", "alice@eni.fr",
                new Adresse("rue A", "44000", "Nantes")
        );
        client.setNoTelephone("0505050505");

        client = clientService.ajouterClient(client);

        Adresse nouvelleAdresse = new Adresse("rue B", "44100", "Rezé");

        Client modifie = clientService.modifierAdresseClient(
                client.getNoClient(), nouvelleAdresse);

        assertThat(modifie.getAdresse().getRue()).isEqualTo("rue B");
        assertThat(modifie.getAdresse().getCodePostal()).isEqualTo("44100");
        assertThat(modifie.getAdresse().getVille()).isEqualTo("Rezé");
    }
}
