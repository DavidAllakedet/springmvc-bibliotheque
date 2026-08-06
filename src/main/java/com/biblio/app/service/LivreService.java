package com.biblio.app.service;

import com.biblio.app.dao.ILivreDao;
import com.biblio.app.dao.IAuteurDao;
import com.biblio.app.dao.LivreDao;
import com.biblio.app.dao.AuteurDao;
import com.biblio.app.dto.LivreDto;
import com.biblio.app.entities.AuteurEntity;
import com.biblio.app.entities.LivreEntity;
import com.biblio.app.mapper.BiblioMapper;

import java.util.List;

public class LivreService {
    private final ILivreDao livreDao = new LivreDao();
    private final IAuteurDao auteurDao = new AuteurDao();

    public LivreDto save(LivreDto dto) {
        AuteurEntity auteur = auteurDao.findById(dto.getAuteurId());
        LivreEntity e = BiblioMapper.toLivreEntity(dto, auteur);
        livreDao.save(e);
        return BiblioMapper.toLivreDto(e);
    }

    public LivreDto update(LivreDto dto) {
        AuteurEntity auteur = auteurDao.findById(dto.getAuteurId());
        LivreEntity e = BiblioMapper.toLivreEntity(dto, auteur);
        livreDao.update(e);
        return BiblioMapper.toLivreDto(e);
    }

    public void delete(Long id) { livreDao.delete(id); }

    public LivreDto findById(Long id) { return BiblioMapper.toLivreDto(livreDao.findById(id)); }

    public List<LivreDto> findAll() { return BiblioMapper.toListLivreDto(livreDao.findAll()); }
}
