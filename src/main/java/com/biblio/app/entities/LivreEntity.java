package com.biblio.app.entities;

import javax.persistence.*;

@Entity
@Table(name = "livres")
public class LivreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 100)
    private String categorie;

    @Column(nullable = false)
    private Integer quantite = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auteur_id")
    private AuteurEntity auteur;

    public LivreEntity() {}

    public LivreEntity(String titre, String isbn, String categorie, Integer quantite, AuteurEntity auteur) {
        this.titre = titre;
        this.isbn = isbn;
        this.categorie = categorie;
        this.quantite = quantite;
        this.auteur = auteur;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public AuteurEntity getAuteur() { return auteur; }
    public void setAuteur(AuteurEntity auteur) { this.auteur = auteur; }
}
