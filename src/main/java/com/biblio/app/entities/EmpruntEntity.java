package com.biblio.app.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprunts")
public class EmpruntEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "livre_id")
    private LivreEntity livre;

    @Column(nullable = false)
    private String nomEmprunteur;

    @Column(name = "date_emprunt", nullable = false)
    private LocalDate dateEmprunt;

    @Column(name = "date_retour")
    private LocalDate dateRetour;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEmprunt statut = StatutEmprunt.EN_COURS;

    public enum StatutEmprunt {
        EN_COURS, RETOURNE, EN_RETARD
    }

    public EmpruntEntity() {}

    public EmpruntEntity(LivreEntity livre, String nomEmprunteur, LocalDate dateEmprunt, LocalDate dateRetour, StatutEmprunt statut) {
        this.livre = livre;
        this.nomEmprunteur = nomEmprunteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LivreEntity getLivre() { return livre; }
    public void setLivre(LivreEntity livre) { this.livre = livre; }

    public String getNomEmprunteur() { return nomEmprunteur; }
    public void setNomEmprunteur(String nomEmprunteur) { this.nomEmprunteur = nomEmprunteur; }

    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }

    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }

    public StatutEmprunt getStatut() { return statut; }
    public void setStatut(StatutEmprunt statut) { this.statut = statut; }
}
