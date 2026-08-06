package com.biblio.app.dao;

import com.biblio.app.entities.LivreEntity;

public class LivreDao extends RepositoryImpl<LivreEntity> implements ILivreDao {
    public LivreDao() { super(LivreEntity.class); }
}
