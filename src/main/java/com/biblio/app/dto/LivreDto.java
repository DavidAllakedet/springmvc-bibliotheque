package com.biblio.app.dto;

public class LivreDto {
    private Long id;
    private String titre;
    private String isbn;
    private String categorie;
    private Integer quantite;
    private Long auteurId;
    private String auteurNom;

    public LivreDto() {}

    public LivreDto(Long id, String titre, String isbn, String categorie, Integer quantite, Long auteurId, String auteurNom) {
        this.id = id; this.titre = titre; this.isbn = isbn; this.categorie = categorie;
        this.quantite = quantite; this.auteurId = auteurId; this.auteurNom = auteurNom;
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
    public Long getAuteurId() { return auteurId; }
    public void setAuteurId(Long auteurId) { this.auteurId = auteurId; }
    public String getAuteurNom() { return auteurNom; }
    public void setAuteurNom(String auteurNom) { this.auteurNom = auteurNom; }
}
