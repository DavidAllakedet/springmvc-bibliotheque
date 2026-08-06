package com.biblio.app.service;

import com.biblio.app.dao.IAuteurDao;
import com.biblio.app.dao.AuteurDao;
import com.biblio.app.dto.AuteurDto;
import com.biblio.app.entities.AuteurEntity;
import com.biblio.app.mapper.BiblioMapper;

import java.util.List;

public class AuteurService {
    private final IAuteurDao dao = new AuteurDao();

    public AuteurDto save(AuteurDto dto) {
        AuteurEntity e = BiblioMapper.toAuteurEntity(dto);
        dao.save(e);
        return BiblioMapper.toAuteurDto(e);
    }

    public AuteurDto update(AuteurDto dto) {
        AuteurEntity e = BiblioMapper.toAuteurEntity(dto);
        dao.update(e);
        return BiblioMapper.toAuteurDto(e);
    }

    public void delete(Long id) { dao.delete(id); }

    public AuteurDto findById(Long id) { return BiblioMapper.toAuteurDto(dao.findById(id)); }

    public List<AuteurDto> findAll() { return BiblioMapper.toListAuteurDto(dao.findAll()); }
}
