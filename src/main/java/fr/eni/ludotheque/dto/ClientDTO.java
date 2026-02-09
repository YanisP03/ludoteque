package fr.eni.ludotheque.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private String nom;
    private String prenom;
    private String email;
    private String noTelephone;

    private String rue;
    private String codePostal;
    private String ville;
}
