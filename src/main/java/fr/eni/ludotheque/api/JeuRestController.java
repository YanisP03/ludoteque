package fr.eni.ludotheque.api;

import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.dto.JeuDisponibiliteDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JeuRestController {

    private final JeuService jeuService;

    public JeuRestController(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    // S4002 - Endpoint public pour trouver les jeux
    @GetMapping("/api/jeux")
    public List<JeuDisponibiliteDTO> getJeux() {
        return jeuService.trouverJeuxAvecDisponibilite();
    }
}
