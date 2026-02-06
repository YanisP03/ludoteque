package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    // Méthode pour trouver les clients dont le nom commence par la chaîne fournie
    List<Client> findByNomStartingWithIgnoreCase(String prefix);
}
