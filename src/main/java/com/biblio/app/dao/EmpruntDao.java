package com.biblio.app.dao;

import com.biblio.app.entities.EmpruntEntity;

public class EmpruntDao extends RepositoryImpl<EmpruntEntity> implements IEmpruntDao {
    public EmpruntDao() { super(EmpruntEntity.class); }
}
