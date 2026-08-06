package com.biblio.app.service;

import com.biblio.app.dao.IEmpruntDao;
import com.biblio.app.dao.ILivreDao;
import com.biblio.app.dao.EmpruntDao;
import com.biblio.app.dao.LivreDao;
import com.biblio.app.dto.EmpruntDto;
import com.biblio.app.entities.EmpruntEntity;
import com.biblio.app.entities.LivreEntity;
import com.biblio.app.mapper.BiblioMapper;

import java.util.List;

public class EmpruntService {
    private final IEmpruntDao empruntDao = new EmpruntDao();
    private final ILivreDao livreDao = new LivreDao();

    public EmpruntDto save(EmpruntDto dto) {
        LivreEntity livre = livreDao.findById(dto.getLivreId());
        EmpruntEntity e = BiblioMapper.toEmpruntEntity(dto, livre);
        empruntDao.save(e);
        return BiblioMapper.toEmpruntDto(e);
    }

    public EmpruntDto update(EmpruntDto dto) {
        LivreEntity livre = livreDao.findById(dto.getLivreId());
        EmpruntEntity e = BiblioMapper.toEmpruntEntity(dto, livre);
        empruntDao.update(e);
        return BiblioMapper.toEmpruntDto(e);
    }

    public void delete(Long id) { empruntDao.delete(id); }

    public EmpruntDto findById(Long id) { return BiblioMapper.toEmpruntDto(empruntDao.findById(id)); }

    public List<EmpruntDto> findAll() { return BiblioMapper.toListEmpruntDto(empruntDao.findAll()); }
}
