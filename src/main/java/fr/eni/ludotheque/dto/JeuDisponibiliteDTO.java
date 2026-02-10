package fr.eni.ludotheque.dto;

import lombok.Data;

@Data
public class JeuDisponibiliteDTO {
    private Integer noJeu;
    private String titre;
    private String description;
    private int nbExemplairesDisponibles;

    public JeuDisponibiliteDTO(Integer noJeu, String titre, String description, int nbExemplairesDisponibles) {
        this.noJeu = noJeu;
        this.titre = titre;
        this.description = description;
        this.nbExemplairesDisponibles = nbExemplairesDisponibles;
    }
}
