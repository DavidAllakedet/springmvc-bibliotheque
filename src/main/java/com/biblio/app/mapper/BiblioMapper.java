package com.biblio.app.mapper;

import com.biblio.app.dto.AuteurDto;
import com.biblio.app.dto.EmpruntDto;
import com.biblio.app.dto.LivreDto;
import com.biblio.app.entities.AuteurEntity;
import com.biblio.app.entities.EmpruntEntity;
import com.biblio.app.entities.LivreEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BiblioMapper {

    public static AuteurDto toAuteurDto(AuteurEntity e) {
        if (e == null) return null;
        return new AuteurDto(e.getId(), e.getNom(), e.getPrenom(), e.getBiographie());
    }

    public static AuteurEntity toAuteurEntity(AuteurDto d) {
        if (d == null) return null;
        AuteurEntity e = new AuteurEntity();
        e.setId(d.getId()); e.setNom(d.getNom()); e.setPrenom(d.getPrenom()); e.setBiographie(d.getBiographie());
        return e;
    }

    public static List<AuteurDto> toListAuteurDto(List<AuteurEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(BiblioMapper::toAuteurDto).collect(Collectors.toList());
    }

    public static LivreDto toLivreDto(LivreEntity e) {
        if (e == null) return null;
        return new LivreDto(e.getId(), e.getTitre(), e.getIsbn(), e.getCategorie(), e.getQuantite(),
                e.getAuteur() != null ? e.getAuteur().getId() : null,
                e.getAuteur() != null ? e.getAuteur().getNom() + " " + e.getAuteur().getPrenom() : null);
    }

    public static LivreEntity toLivreEntity(LivreDto d, AuteurEntity auteur) {
        if (d == null) return null;
        LivreEntity e = new LivreEntity();
        e.setId(d.getId()); e.setTitre(d.getTitre()); e.setIsbn(d.getIsbn());
        e.setCategorie(d.getCategorie()); e.setQuantite(d.getQuantite()); e.setAuteur(auteur);
        return e;
    }

    public static List<LivreDto> toListLivreDto(List<LivreEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(BiblioMapper::toLivreDto).collect(Collectors.toList());
    }

    public static EmpruntDto toEmpruntDto(EmpruntEntity e) {
        if (e == null) return null;
        return new EmpruntDto(e.getId(),
                e.getLivre() != null ? e.getLivre().getId() : null,
                e.getLivre() != null ? e.getLivre().getTitre() : null,
                e.getNomEmprunteur(), e.getDateEmprunt(), e.getDateRetour(),
                e.getStatut() != null ? e.getStatut().name() : null);
    }

    public static EmpruntEntity toEmpruntEntity(EmpruntDto d, LivreEntity livre) {
        if (d == null) return null;
        EmpruntEntity e = new EmpruntEntity();
        e.setId(d.getId()); e.setLivre(livre); e.setNomEmprunteur(d.getNomEmprunteur());
        e.setDateEmprunt(d.getDateEmprunt()); e.setDateRetour(d.getDateRetour());
        if (d.getStatut() != null) e.setStatut(EmpruntEntity.StatutEmprunt.valueOf(d.getStatut()));
        return e;
    }

    public static List<EmpruntDto> toListEmpruntDto(List<EmpruntEntity> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(BiblioMapper::toEmpruntDto).collect(Collectors.toList());
    }
}
