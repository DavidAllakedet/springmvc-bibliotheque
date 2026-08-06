package com.biblio.app.dto;

public class AuteurDto {
    private Long id;
    private String nom;
    private String prenom;
    private String biographie;

    public AuteurDto() {}

    public AuteurDto(Long id, String nom, String prenom, String biographie) {
        this.id = id; this.nom = nom; this.prenom = prenom; this.biographie = biographie;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getBiographie() { return biographie; }
    public void setBiographie(String biographie) { this.biographie = biographie; }
}
