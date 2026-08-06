package com.biblio.app.entities;

import javax.persistence.*;

@Entity
@Table(name = "auteurs")
public class AuteurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(columnDefinition = "TEXT")
    private String biographie;

    public AuteurEntity() {}

    public AuteurEntity(String nom, String prenom, String biographie) {
        this.nom = nom;
        this.prenom = prenom;
        this.biographie = biographie;
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
