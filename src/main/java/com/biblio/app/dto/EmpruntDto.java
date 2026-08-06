package com.biblio.app.dto;

import java.time.LocalDate;

public class EmpruntDto {
    private Long id;
    private Long livreId;
    private String livreTitre;
    private String nomEmprunteur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private String statut;

    public EmpruntDto() {}

    public EmpruntDto(Long id, Long livreId, String livreTitre, String nomEmprunteur, LocalDate dateEmprunt, LocalDate dateRetour, String statut) {
        this.id = id; this.livreId = livreId; this.livreTitre = livreTitre;
        this.nomEmprunteur = nomEmprunteur; this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour; this.statut = statut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLivreId() { return livreId; }
    public void setLivreId(Long livreId) { this.livreId = livreId; }
    public String getLivreTitre() { return livreTitre; }
    public void setLivreTitre(String livreTitre) { this.livreTitre = livreTitre; }
    public String getNomEmprunteur() { return nomEmprunteur; }
    public void setNomEmprunteur(String nomEmprunteur) { this.nomEmprunteur = nomEmprunteur; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }
    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
