package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer>{

    @Query("""
        SELECT COUNT(e)
        FROM Exemplaire e
        WHERE e.jeu.noJeu = :jeuId
        AND e.louable = true
    """)
    int countDisponiblesByJeuId(@Param("jeuId") Integer jeuId);
}
