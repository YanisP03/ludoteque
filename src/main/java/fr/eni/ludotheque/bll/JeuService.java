package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.dto.JeuDisponibiliteDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JeuService {

    private final JeuRepository jeuRepository;
    private final ExemplaireRepository exemplaireRepository;

    public JeuService(JeuRepository jeuRepository, ExemplaireRepository exemplaireRepository) {
        this.jeuRepository = jeuRepository;
        this.exemplaireRepository = exemplaireRepository;
    }

    public List<JeuDisponibiliteDTO> trouverJeuxAvecDisponibilite() {
        List<Jeu> jeux = jeuRepository.findAll();

        return jeux.stream().map(jeu -> {
            int dispo = exemplaireRepository.countDisponiblesByJeuId(jeu.getNoJeu());
            return new JeuDisponibiliteDTO(
                    jeu.getNoJeu(),
                    jeu.getTitre(),
                    jeu.getDescription(),
                    dispo
            );
        }).collect(Collectors.toList());
    }
}
